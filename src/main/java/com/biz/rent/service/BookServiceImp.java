package com.biz.rent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.rent.mapper.BookDao;
import com.biz.rent.model.BookVO;

@Service
public class BookServiceImp implements BookService {

	@Autowired
	BookDao bDao;
	
	@Override
	public List<BookVO> selectAll() {
		// 저장된 정보의 list를 저장 후 보여준다.
		List<BookVO> bookList = bDao.selectAll();
		return bookList;
	}

	@Override
	public BookVO findById(long book_seq) {
		// TODO Auto-generated method stub
		BookVO vo = bDao.findById(book_seq);
		return vo;
	}

	@Override
	public int insert(BookVO bookVO) {
		// TODO Auto-generated method stub
		int ret = bDao.insert(bookVO);
		return ret;
	}

	@Override
	public int update(BookVO bookVO) {
		// TODO Auto-generated method stub
		int ret = bDao.update(bookVO);
		return ret;
	}

	@Override
	public int delete(long book_seq) {
		// TODO Auto-generated method stub
		int ret = bDao.delete(book_seq);
		return ret;
	}

	@Override
	public List<BookVO> getSearchList(String s_string) {

		try {
		// isbn은 바코드 = 그 책의 고유한 번호(국제적으로..)
		// 넘겨받은 s_string을 long형으로 바꾸려고 시도
		Long.valueOf(s_string);
		
		/*
		 * 만약 오류가 발생하지 않았다면
		 * s_string은 숫자로 변환이 가능한 문자열
		 * 986565656 이런 형식의 ISBN
		 * 미래라는 글자를 입력하거나 98...바코드를 입력하면
		 * 그러니까 둘중의 하나를 입력하면 도서 정보를 찾을 수 있게 하는 것.
		 * 
		 */
		
		return bDao.getSearchListByIsbn(s_string);

		
		}catch(Exception e) {
			
		}
		return bDao.getSearchList(s_string);
	}

}
