package com.biz.rent.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.rent.mapper.UserDao;
import com.biz.rent.model.BookVO;
import com.biz.rent.model.UserVO;
import com.biz.rent.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
@SessionAttributes({"userVO","bookVO"}) //두개의 attribute를 쓰겠다
public class AdminController {
	
	@Autowired
	UserDao uDao;
	
	@Autowired
	BookService bService;
	
	/*
	 * SessionAttribute를 사용하려면 반드시
	 * 해당 객체의 초기화 메서드가 있어야 한다.
	 */
	@ModelAttribute("userVO")
	public UserVO newUser() {
		UserVO vo = new UserVO();
		
		LocalDateTime lt = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		vo.setUser_reg_date(df.format(lt)); // 현재 날짜로 가입일을 세팅
		vo.setUser_out_yn("N");
		
		return vo;
	}
	
	// 비어있는 bookVO를 생성하는 부분
	@ModelAttribute("bookVO")
	public BookVO newBook() {
		BookVO vo = new BookVO();
		
		vo.setBook_rent_yn("Y");
		
		return vo;
	}

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home() {
		return "admin_home";
	}
	
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public String user(@ModelAttribute("userVO") UserVO userVO, Model model) {
		
		List<UserVO> userList =uDao.selectAll();
		model.addAttribute("userVO",userVO);
		model.addAttribute("LIST",userList);
		model.addAttribute("BODY","USER_LIST");
		return "admin_home";
	}
	/*
	 * (ModelAttribute가 하는 일)
	 * model로 받은 uservo를 다시 userVO로 담음
	 * 매개변수로 받은 vo를 모델에 심어서 보내기만을 했는데,
	 * 현재 userVO에는 아무것도 없는 상태임
	 * 그러면 userVO가 아무것도 없는 상태다라하면 위에 있는 
	 * UserVO vo = new UserVO();를 만들고
	 * 아래의 userVO에 담아둠.
	 * 
	 */
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public String user(@ModelAttribute("userVO") UserVO userVO, Model model,
						SessionStatus session) {
		
		log.debug("UserVO:" + userVO.toString());
		uDao.insert(userVO);
		
		// 현재 session에 담겨있는 값들을 초기화 시키는 것.
		// 초기화를 시키지 않으면 새로운 form이 열렸을때 이전의 값을
		// 그대로 보여주는 현상이 생김
		session.setComplete();
		//model.addAttribute("BODY","USER_LIST");
		return "redirect:/admin/user";
	}
	
	/*
	 * web에서 script를 실행할때 ?id 대신 user_seq를 변수로 설정했기 때문에
	 * 그 값은 userVO를 통해서 받을 수 있다.
	 * 
	 */
	@RequestMapping(value="/user/update",method=RequestMethod.GET)
	public String user_update(@ModelAttribute("userVO") UserVO userVO, Model model) {
		
		//user_admin에서 user_seq의 값을 받으려면 여기서 user_seq변수선언해줘야함
		List<UserVO> userList =uDao.selectAll();
		
		//1번 방법
		long user_seq = userVO.getUser_seq();
		userVO = uDao.findById(user_seq);
		
		//2번 방법
		//userVO = uDao.findById(userVO.getUser_seq());
		
		model.addAttribute("userVO",userVO);
		model.addAttribute("LIST",userList);
		model.addAttribute("BODY","USER_LIST");
		return "admin_home";
	}
	
	@RequestMapping(value="/user/update",method=RequestMethod.POST)
	public String user_update(@ModelAttribute("userVO") UserVO userVO, Model model,
						SessionStatus session) {
		
		log.debug("UserVO:" + userVO.toString());
		uDao.update(userVO);
		
		// 현재 session에 담겨있는 값들을 초기화 시키는 것.
		// 초기화를 시키지 않으면 새로운 form이 열렸을때 이전의 값을
		// 그대로 보여주는 현상이 생김
		session.setComplete();
		//model.addAttribute("BODY","USER_LIST");
		return "redirect:/admin/user";
	}
	
	@RequestMapping(value="/user/delete",method=RequestMethod.GET)
	public String user_delete(@RequestParam long id) {
		
		uDao.delete(id);
		return "redirect:/admin/user";
	}
	
	// 메뉴를 클릭했을때,
	// 도서정보 메뉴를 클릭했을때 호출되는 부분
	@RequestMapping(value="/book",method=RequestMethod.GET)
	public String book(@ModelAttribute BookVO bookVO, Model model) {
		
		// List를 보여주기 위해서 List를 생성하고
		//리스트라는 이름으로 bookList를 넘겨준다.
		List<BookVO> bookList =bService.selectAll();
		model.addAttribute("LIST",bookList);
		
		// 입력폼에 빈값을 넘겨주기 위해서 vo를 작성
		model.addAttribute("bookVO",bookVO);
		
		model.addAttribute("BODY","BOOK_LIST");
		return "admin_home";
	}
	// 새로운 도서정보를 저장하고 다시 도서 정보 리스트 보기로 화면 전환
	// post로 만들어야함.
	@RequestMapping(value="/book",method=RequestMethod.POST)
	public String book(@ModelAttribute("bookVO") BookVO bookVO, Model model,
						SessionStatus session) {
		
		
		// service, dao를 호출해서 데이터를 insert
		bService.insert(bookVO);
		
		// 현재 session에 담겨있는 값들을 초기화 시키는 것.
		// 초기화를 시키지 않으면 새로운 form이 열렸을때 이전의 값을
		// 그대로 보여주는 현상이 생김
		session.setComplete();
		return "redirect:/admin/book";
	}

	/*
	 * GET 방식으로 WEB으로부터 서버에 데이터를 보낼때는
	 * 	?변수=값 형식으로 보내고 받을때는 @RequestParam("변수") 변수명
	 *	PathVariable은 주소값에 변수를 실어 보내는 방식
	 *	PathVariable("id")라고 지정을 하고
	 *	mapping value=".../delete/{id}라고 지정을 해 두면
	 *	web에서 보낼때
	 *		../delete/3이라고 설정을 하면
	 *		3이라는 값이 id 변수에 담겨서 서버에 전달이 된다.
	 *(상당히 세련된 기법)
	 *	만약 주소창에 그냥 ../delete/라고만 보내면 404 오류가 발생한다.
	 *만약 id 변수에 null값이 전달되어 오면 대책이 없다 == 값을 반드시 가지고 있어야 한다.
	 *	(?id= 방식은 null값이 전달되어 대책을 서버에서 마련할 수 있다.)  
	 *
	 */
	
	@RequestMapping(value="/book/delete/{id}",method=RequestMethod.GET)
	public String book_delete(@PathVariable("id") long id) {
		
		bService.delete(id);
		return "redirect:/admin/book";
	}
	
	@RequestMapping(value="/book/delete",method=RequestMethod.GET)
	public String book_delete() {
		// 값이 전달되지 않았다는 메시지를 포함해서 다른 페이지로 redirect 시킨다.
		return "redirect:/admin/book";
	}
	
	@RequestMapping(value="/book/update",method=RequestMethod.GET)
	public String book_update(@ModelAttribute BookVO bookVO, Model model) {
		
		long book_seq = bookVO.getBook_seq();
		bookVO = bService.findById(book_seq);
		model.addAttribute("bookVO", bookVO);
		
		List<BookVO> bookList = bService.selectAll();
		model.addAttribute("LIST",bookList);
		
		model.addAttribute("BODY", "BOOK_LIST");
		
		
		return "admin_home";
	}
	
	@RequestMapping(value="/book/update",method=RequestMethod.POST)
	public String book_update(@ModelAttribute BookVO bookVO, Model model, SessionStatus session) {
		
		bService.update(bookVO);
		
		session.setComplete();
		
		return "redirect:/admin/book";
	}
}
