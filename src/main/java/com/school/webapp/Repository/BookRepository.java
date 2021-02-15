package com.school.webapp.Repository;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface BookRepository extends CrudRepository<BookEntity,Integer> {

    @Query( value = "Select * from book_entity where title like %?1% and year =?2 and term=?3 and schoolid=?4",nativeQuery = true)
    ArrayList<BookEntity> findByNameAndSessionAndTerm(String bookname,String session, String term,String schoolid);
    @Query (value = "Select * from book_entity where schoolid=?1",nativeQuery = true)
    ArrayList<BookEntity> getAllBook(String schoolid);

}
