package org.beginningee6.book.chapter12.ex06;

import org.apache.log4j.Logger;
import org.beginningee6.book.chapter12.Book;
import org.beginningee6.book.chapter12.BookEJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "myBookController06")
@ViewScoped
public class BookController implements Serializable {
    private static final Logger log = Logger.getLogger(BookController.class);

    @EJB
    private BookEJB bookEJB;

    @ManagedProperty(value = "#{initController06.defaultBook}")
    private Book book = new Book();

    private LazyDataModel<Book> model = new BooksModel();

    public String doCreateBook() {
        book = bookEJB.createBook(book);
        //return "listBooks.xhtml?faces-redirect=true";
        //return "success";
        return "pretty:list";
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

//    public LazyDataModel<Book> getBookList() {
//        return model;
//    }

    public List<Book> getBookList() {
        log.info("getBookList");
        List<Book> books = bookEJB.findBooks();
        for (int i = 1; i < 100; i++) {
            books.add(new Book("default title" + i, i * 1f, "default description", "0000-000" + i, 500 - i * 10, true));
        }
        return books;
    }

    public int sort(Object o1, Object o2) {
        log.info("sort " + o1 + " - " + o2);
        return 0;
    }

    class BooksModel extends LazyDataModel<Book> {
        @Override
        public List<Book> load(int first, int pageSize, String sortField, final SortOrder sortOrder, Map<String, String> filters) {
            log.info("load: field: " + sortField + " order: " + sortOrder);

            List<Book> books = bookEJB.findBooks();
            for (int i = first; i < first + pageSize; i++) {
                books.add(new Book("default title" + i, i * 1f, "default description", "0000-000" + i, 500 - i * 10, true));
            }
            Collections.sort(books, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    if (sortOrder == SortOrder.DESCENDING)
                        return o1.getNbOfPage() - o2.getNbOfPage();
                    else
                        return o2.getNbOfPage() - o1.getNbOfPage();
                }
            });
            setRowCount(100);
            return books;
        }
    }

}