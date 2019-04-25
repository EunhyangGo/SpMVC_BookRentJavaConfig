package com.biz.rent.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.rent.model.CartVO;
import com.biz.rent.model.RentVO;
import com.biz.rent.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rent")
public class CartController {

	@Autowired
	CartService cService;
	
	
	//@ResponseBody
	@RequestMapping(value="/cart",method=RequestMethod.POST)
	public String cart(@ModelAttribute("rentVO") RentVO rentVO, Model model,
					   HttpSession httpSession) {
	/*
	 * httpsession에 혹시 cart라는 정보가 담겨있으면 그 정보를 cart에 담아라
	 * 처음에는 없기때문에 null이 나옴.
	 * 우선 null이면 새로운 카트를 하나 만들고, 위에서 넘겨온 rentvo의 정보를 추가하라는 말
	 * 기존에 있었으면 뒤에 추가가 될 것이고, 기존에 없었으면 새로운 정보가 만들어졌으면 그곳에 추가(ArrayList)
	 * 확인을 하려고 우선 return문에 ok라고 넣음
	 */
		try {
		List<CartVO> cart = (List)httpSession.getAttribute("CART");
		// 결과를 add해놓고 카드에 다시 담겠다
		List<CartVO> returnCart = cService.addCart(cart, rentVO);
		
		// returnCart는 0보다 크다는 말. null이 아니니까
		if(returnCart != null) {
		
			// 현재 카트의 개수 > 0 
			// returnCart의 값은 null이 아닌경우
			// 최소한 1개 이상의 개수를 가지고 있다.
			// 그 개수 값을 변수에 임시 담고 
			int intLen = returnCart.size();
			
			// 카트 리스트의 가장 마지막 항목의 cart_seq칼럼에
			// 그 값을 저장해 준다.
			returnCart.get(intLen -1).setCart_seq(intLen);
			
			// returnCart의 0번째 요소에 cart_seq는 1이 되고
			// 순서대로 2, 3, 4, 5 값이 연속해서 부여된다.
			// cart_seq에 저장된 값은 List의 순서가 바뀌어도 변하지 않는 고유 값이 될 것이다.
			// 이 값을 참조하여 삭제를 하도록 시도할 것이다.
			
			httpSession.setAttribute("CART",returnCart);
			
			for(CartVO vo: returnCart) {
				log.debug("Cart" + vo.toString());
			}
				
		}
		
		
		return "rent_body/rent/rent_cart";
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		model.addAttribute("rentVO",rentVO);
		return null;
	}
	
	@RequestMapping(value="/cart_clear",method=RequestMethod.GET)
	public String cart_clear(HttpSession httpSession) {
		
		// 모든 세션을 제거하라
		// 혹시 모를 다른 session도 모두 삭제하기 때문에 가급적 사용 자제
		//httpSession.invalidate();
		
		httpSession.removeAttribute("CART");
		httpSession.setAttribute("CART", null);
		
		return "rent_body/rent/rent_cart";
	}
	
	// 순서값을 가져와서 리스트중 일부를 삭제하는 방법
	@RequestMapping(value="/cart_index_delete/{index}",method=RequestMethod.GET)
	public String cart_index_delete(@PathVariable("index") int index, HttpSession httpSession) {
		
		if(index > -1) {
			List<CartVO> cartList = (List)httpSession.getAttribute("CART");
			if(cartList != null) {
				cartList.remove(index);
				httpSession.setAttribute("CART",cartList);
			}
		}
		return "rent_body/rent/rent_cart";
	}
	
	// 고유의 값(sequence 칼럼)의 값을 참조해서 삭제
	@RequestMapping(value="/cart_seq_delete/{seq}",method=RequestMethod.GET)
	public String cart_seq_delete(@PathVariable("seq") int seq, HttpSession httpSession) {
		
		List<CartVO> cartList = (List)httpSession.getAttribute("CART");
		// cart List가 null이 아니면 for문을 반복하고 
		// cart_seq랑 seq랑 같으면 삭제를 하고 멈춰
		if(cartList != null) {
			int intLen = cartList.size();
			//for(int i = 0; i < cartList.size(); i++)
			for(int i = 0; i< intLen; i++) {
				if(cartList.get(i).getCart_seq() == seq) {
					cartList.remove(i);
					break;
				}
			}
			httpSession.setAttribute("CART",cartList);
		}
		return "rent_body/rent/rent_cart";
	}
}
