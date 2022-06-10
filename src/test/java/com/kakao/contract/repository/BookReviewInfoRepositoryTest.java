package com.kakao.contract.repository;

import com.kakao.contract.entity.Book;
import com.kakao.contract.entity.BookReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookReviewInfoRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookReviewInfoRepository bookReviewInfoRepository;

    @Test
    public void crud2(){
        givenBookReviewInfo(givenBook());
    }


    private void givenBookReviewInfo(Book book){

        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(book);
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);
        bookReviewInfoRepository.save(bookReviewInfo);

        System.out.println(">>> " + bookReviewInfoRepository.findAll());

        Book result = bookReviewInfoRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBook();

        System.out.println(">>> " + result);

    }

    private Book givenBook(){

        Book book = new Book();
        book.setName("Noise");
        book.setAuthorId(1L);
        book.setPublisherId(1L);

        return bookRepository.save(book);
    }

}
