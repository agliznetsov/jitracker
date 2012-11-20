package org.beginningee6.book.chapter12;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class BookEJB {

//    @PersistenceContext(unitName = "chapter12PU")
//    private EntityManager em;

    public List<Book> findBooks() {
//        Query query = em.createNamedQuery("findAllBooks");
//        return query.getResultList();
        List<Book> books = new ArrayList<Book>();
        books.add(new Book());
        return books;
    }

    public Book createBook(Book book) {
//        em.persist(book);
        return book;
    }
}