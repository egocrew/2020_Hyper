package com.min.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.min.app.dto.Day_Dto;
import com.min.app.dto.File_Dto;
import com.min.app.dto.Key_Dto;
import com.min.app.dto.Login_Dto;
import com.min.app.dto.Memo_Dto;
import com.min.app.dto.Setting_Dto;
import com.min.app.dto.User_Dto;
import com.min.app.model.event.Event_IService;
import com.min.app.model.file.File_IService;
import com.min.app.model.log.Log_IService;
import com.min.app.model.user.User_IService;

@Controller
public class BasicController {

	private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

	@Autowired
	BCryptPasswordEncoder passEncoder;

	@Autowired
	Event_IService eService;
	
	@Autowired
	User_IService uService;

	@Autowired
	File_IService fService;
	
	@Autowired
	Log_IService lService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(String nickname, HttpSession session, Model model, HttpServletRequest request) {
		User_Dto user_dto = (User_Dto)session.getAttribute("Ldto");
		// 닉네임이 입력되지 않았다면 기본 설정
		if(nickname==""||nickname==null) { 
			if (user_dto==null) {  // 로그인 안했다면
				nickname="Hyper";
			}else { // 로그인했다면
				nickname=user_dto.getSetting_nck();
			}
		}
		
		Setting_Dto setting_dto = uService.getSettingInfo(nickname);
		File_Dto file_dto = fService.imageSe(setting_dto.getUser_seq());
		Map<String,String[]> keyList = eService.selectKey(setting_dto.getUser_seq());
		List<Day_Dto> dayList = eService.selectDay(setting_dto.getUser_seq());
		List<Memo_Dto> memoList = eService.getMemo(setting_dto.getUser_seq());
		
		model.addAttribute("backImgPath", file_dto.getSto_image_name()); //배경화면조회
		model.addAttribute("Sdto", setting_dto); //환경설정 조회
		model.addAttribute("linkMode", keyList); //단축키 조회
		model.addAttribute("dayList",dayList); //디데이 조회
		model.addAttribute("memoList", memoList);//메모 조회
		
		
		// 회원 접근
		if (user_dto!=null) { 
			if (user_dto.getSetting_nck().equalsIgnoreCase(nickname)) { // 자기 하이퍼일때
				lService.setLog(user_dto.getSetting_nck()+"님이 "+nickname+"님의 Hyper를 방문하셨습니다.", getClientIP(request));
				model.addAttribute("isMe", true);
				return "indexUser";
			}else{ // 다른 하이퍼일때
				lService.setLog(nickname+"님의 자신의 Hyper를 방문하셨습니다.", getClientIP(request));
				model.addAttribute("isMe", false);
				return "indexSocial"; 
			}
		}
		// 비회원 접근
		lService.setLog("비회원님이 "+nickname+"님의 Hyper를 방문하셨습니다.", getClientIP(request));
		model.addAttribute("isMe", false);
		return "indexDefault";
	}


	//이메일 중복 체크
	@RequestMapping(value = "/checkemail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkEmail(String email){
		Map<String, String> map = new HashMap<String, String>();
		String checke = uService.emailCheck(email);
		map.put("checke", checke);
		
		return map;
	}
	//닉네임 중복 체크
	@RequestMapping(value = "/checknick",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkNick(String nick){
		Map<String, String> map = new HashMap<String, String>();
		String checkn = uService.nckCheck(nick);
		System.out.println(checkn);
		map.put("checkn", checkn);
		
		return map;
	}

	//회원가입
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String register(User_Dto dto, HttpServletRequest request) {	
		String inputPass = dto.getUser_pw();
		String pass = passEncoder.encode(inputPass);
		
		dto.setUser_pw(pass);
		boolean isc = uService.signUp(dto);
		if(isc) {
			fService.imageIn(dto.getUser_email());
		}
		lService.setLog(dto.getSetting_nck()+"님이 회원가입 하셨습니다.", getClientIP(request));

		return "redirect:/";
	}
	
	//로그인
	@RequestMapping(value="/signIn",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> signIn(String email, String password,HttpSession session, Model model, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Login_Dto lDTO = uService.signIn(email,password);
		User_Dto uDTO = lDTO.getUser_dto();
		// 로그인 가능여부 확인
		if (lDTO.getResult()) {
			// 세션에 회원 정보 올리기
			session.setAttribute("Ldto", uDTO);
			model.addAttribute("arUserSet", uService.selectUserSet(uDTO.getUser_seq()));
			lService.setLog(uDTO.getSetting_nck()+"님이 로그인 하셨습니다.", getClientIP(request));
			// 결과 값 리턴하기
			map.put("checkl", "true");
		}else {
			map.put("checkl", "false");
		}
		
		return map;
	}

	//로그아웃

	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {
		User_Dto dto = (User_Dto)session.getAttribute("Ldto");
		lService.setLog(dto.getSetting_nck()+"님이 로그아웃 하셨습니다.", getClientIP(request));
		session.invalidate();
		return "redirect:/";
	}

	//비밀번호 변경
	@RequestMapping(value = "/pwChange",method = RequestMethod.POST)
	public String resetPw(User_Dto dto,HttpSession session, HttpServletRequest request) {
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");
		if(Ldto!=null) {
			String inputPass = dto.getUser_pw();
			String pass = passEncoder.encode(inputPass);
			Ldto.setUser_pw(pass);
			boolean isc = uService.resetPw(Ldto);
			if(isc) {
				lService.setLog(Ldto.getSetting_nck()+"님이 비밀번호 변경 하셨습니다.", getClientIP(request));
				return "redirect:/";
			}
		}

		return "redirect:/";
	}
	//상태메세지 및 테마 변경
	@RequestMapping(value="/updateUserSet",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateUserSet(String message, String theme, HttpSession session, HttpServletRequest request) {
		// 화면에서 가져오기 값
		User_Dto uDto = (User_Dto)session.getAttribute("Ldto");
		int user_seq = uDto.getUser_seq();
		Setting_Dto settingDto = new Setting_Dto(user_seq, message, "", theme);
		// 적용하기
		boolean isc = uService.updateUserSet(settingDto);
		Map<String, String> map = new HashMap<String, String>();
		// 새로 초기화해주기
		if (isc) {
			session.removeAttribute("arUserSet");
			session.setAttribute("arUserSet", uService.selectUserSet(user_seq));
			lService.setLog(uDto.getSetting_nck()+"님이 환경설정 변경 하셨습니다.", getClientIP(request));
			
			map.put("checkS", "true");
		}else {
			map.put("checkS", "false");
		}

		return map;
	}

	//닉네임 변경
	@RequestMapping(value="/changeNick",method=RequestMethod.POST)
	public String changeNick(HttpSession session,String setting_nck, HttpServletRequest request) {
		User_Dto uDto = (User_Dto)session.getAttribute("Ldto");
		uDto.setSetting_nck(setting_nck);
		uService.resetNck(uDto);
		lService.setLog(uDto.getSetting_nck()+"님이 닉네임 변경 하셨습니다.", getClientIP(request));
		return "redirect:/logout";
	}

	//비밀번호 재설정
	@RequestMapping(value="/changePw",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> changePw(HttpSession session,String oldPw, String newPw) {
		System.out.println("changePw");
		Map<String,String> map = new HashMap<String, String>();
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");

		boolean passMatch = passEncoder.matches(oldPw,Ldto.getUser_pw());
		if (passMatch) {
			System.out.println("비밀번호암호화대조성공");
			String pass = passEncoder.encode(newPw);
			Ldto.setUser_pw(pass);
			uService.resetPw(Ldto);
			map.put("checkp", "true");
			return map;
		}else {
			System.out.println("비밀번호암호화대조실패");
			map.put("checkp", "false");
			return map;
		}
	}
	//회원탈퇴
	@RequestMapping(value = "/signDown", method=RequestMethod.GET)
	public String signDown(HttpSession session, HttpServletRequest request) {
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");
		int user_seq = Ldto.getUser_seq();
		uService.signDown(user_seq);
		lService.setLog(Ldto.getSetting_nck()+"님이 회원탈퇴 하셨습니다.", getClientIP(request));
		return "redirect:/logout";
	}

	//파일업로드
	@RequestMapping(value = "/imageIn",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> imageIn(HttpSession session, MultipartHttpServletRequest mtf) throws IllegalStateException, IOException {
		Map<String,String> map = new HashMap<String, String>();
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");

		MultipartFile file = mtf.getFile("file");
		boolean isc = file.isEmpty();
		boolean isk = false;
		int user_seq = Ldto.getUser_seq();
		if(isc==true) {
			File_Dto fDTO = new File_Dto(user_seq,"","",0);
			isk = fService.imageUp(fDTO);
		}else {
			String oriName = file.getOriginalFilename();
			long fileSize = file.getSize();
			String ext = oriName.substring(oriName.lastIndexOf("."));
			String stoName = UUID.randomUUID()+ext;
			String filePath = mtf.getSession().getServletContext().getRealPath("/upload");
			System.out.println(mtf.getContextPath());

			File dir = new File(filePath);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			//transferTo 메소드는 파일이 서버의 realPath에 업로드되도록 한다.
			file.transferTo(new File(filePath,stoName));
			logger.info("경로 : "+filePath+"/"+stoName);
			File_Dto fDTO = new File_Dto(user_seq,oriName,stoName,fileSize);
			isk = fService.imageUp(fDTO);
			map.put("path", filePath+"/"+stoName);
		}
		map.put("isk", isk+"");
		return map;
	}

	// 편집하기
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editMode(HttpSession session, Model model, HttpServletRequest request) {
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");
		Map<String,String[]> keyList = eService.selectKey(Ldto.getUser_seq());
		model.addAttribute("linkMode", keyList);
		lService.setLog(Ldto.getSetting_nck()+"님이 즐겨찾기 변경 하셨습니다.", getClientIP(request));
		return "indexEdit";
	}
	
	// 편집모드 저장
	@RequestMapping(value="/editSave", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> editSave(HttpSession session, String key, String link, String icon) {
		Map<String,String> map = new HashMap<String, String>();
		Key_Dto dto = new Key_Dto();
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");
		boolean isc = false;
		
		dto.setUser_seq(Ldto.getUser_seq());
		dto.setLink_key(key);
		dto.setLink_icon(icon);
		dto.setLink_url(link);
		
		isc = eService.updateKey(dto);
		map.put("checku", isc+"");
		
		return map;
	}
	
	//메모 작성
	@RequestMapping(value = "/memoInsert",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> memoInsert(HttpSession session,Memo_Dto dto) {
		Map<String,String> map = new HashMap<String, String>();
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");
		Memo_Dto Mdto = new Memo_Dto(Ldto.getUser_seq(), 0, dto.getMemo_con());
		
		map.put("checkm", eService.setMemo(Mdto)+"");
		
		return map;
	}
	
	//메모 삭제
	@RequestMapping(value = "/memoDelete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> memoDelete(int memo_seq) {
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("checkm",eService.removeMemo(memo_seq)+"");

		return map;
	}

	//디데이 생성
	@RequestMapping(value = "/insertDay",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> insertDay(HttpSession session, String day_title, String day_date) {
		User_Dto Ldto = (User_Dto)session.getAttribute("Ldto");
		Day_Dto dto = new Day_Dto(Ldto.getUser_seq(), day_title, day_date,"", 0,"N");
		System.out.println(day_date);
		Map<String, String> map = new HashMap<String, String>();
		
		boolean isc = false;
		isc = eService.insertDay(dto);
		
		map.put("checkd", isc+"");
		return map;
	}

	//디데이 삭제
	@RequestMapping(value = "/deleteDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> deleteDay(String dayseq) {
		Map<String,String> map = new HashMap<String, String>();
		boolean isc = false;
		isc = eService.deleteDay(Integer.parseInt(dayseq));
		map.put("dDel", isc+"");
		return map;
	}
	
	//대표 디데이 설정
	@RequestMapping(value = "/showSetDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> showSetDay(HttpSession session, String dayseq) {
		Map<String,String> map = new HashMap<String, String>();
		boolean isc = false;
		User_Dto uDTO = (User_Dto)session.getAttribute("Ldto");
		Day_Dto dto = new Day_Dto();
		dto.setDay_seq(Integer.parseInt(dayseq));
		dto.setUser_seq(uDTO.getUser_seq());
		isc = eService.showSetDay(dto);
		map.put("checkS", isc+"");
		return map;
	}
	
	//IP 확인
	public String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR"); 
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("Proxy-Client-IP");
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("WL-Proxy-Client-IP");  // 웹로직
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getRemoteAddr() ;
		}
		
		return ip;
		
	}

}
