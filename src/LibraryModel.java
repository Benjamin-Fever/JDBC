/*
 * LibraryModel.java
 * Author:
 * Created on:
 */



import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryModel {

    // For use in creating dialogs and making them modal
    private JFrame dialogParent;
    private Connection con = null;

    public LibraryModel(JFrame parent, String userid, String password) {
        // Register Driver
        String url = "jdbc:postgresql://db.ecs.vuw.ac.nz/" + userid + "_jdbc";
        try{ Class.forName("org.postgresql.Driver"); }
        catch (ClassNotFoundException e) { throw new RuntimeException("Can not find driver class"); }

        try{con = DriverManager.getConnection(url, userid, password);}
        catch (SQLException e) { throw new RuntimeException("Cannot connect\n" + e.getMessage()); }
        dialogParent = parent;
    }

    public String bookLookup(int isbn) {
        ResultSet rs = query(
"SELECT book.ISBN, book.Title, book.Edition_No, book.NumOfCop, book.NumLeft, _author.Name, _author.Surname" +
"\nFROM book" +
"\nJOIN book_Author bookAuthor ON book.ISBN = bookAuthor.ISBN" +
"\nJOIN author _author ON _author.AuthorId = bookAuthor.AuthorId" +
"\nWHERE book.ISBN = '" + isbn + "'");
        Map<Integer, List<String>> info = new HashMap<>();
        try {
            while (rs.next()){

                int _isbn = rs.getInt("ISBN");
                String title = rs.getString("Title");
                int edition_num = rs.getInt("Edition_No");
                int num_of_cop = rs.getInt("NumOfCop");
                int num_left = rs.getInt("NumLeft");
                String author_name = rs.getString("Name").replace(" ", "");
                author_name += " " + rs.getString("Surname").replace(" ", "");
                if (info.containsKey(_isbn)){
                    info.get(_isbn).add(author_name);
                }
                else{
                    info.put(_isbn, new ArrayList<>());
                    info.get(_isbn).add(Integer.toString(_isbn));
                    info.get(_isbn).add(title);
                    info.get(_isbn).add(Integer.toString(edition_num));
                    info.get(_isbn).add(Integer.toString(num_of_cop));
                    info.get(_isbn).add(Integer.toString(num_left));
                    info.get(_isbn).add(author_name);
                }
            }
        }
        catch (SQLException e){ throw new RuntimeException(e.getMessage()); }

        if (!info.containsKey(isbn)){ return "No ISBN: " + isbn; }
        String data = "Book Lookup:\n" + info.get(isbn).get(0) + ": " + info.get(isbn).get(1) + "\n" + "\tEdition: " + info.get(isbn).get(2)
                + "- Number of copies: " + info.get(isbn).get(3) + "- Copies left:" + info.get(isbn).get(4);
        if (info.get(isbn).size() > 5) {
            data += "\n\tAuthor/s: ";
            for (int i = 5; i < info.get(isbn).size(); i++) {
                data += info.get(isbn).get(i);
                if (i+1 < info.get(isbn).size()) data += ", ";
            }
        }
        else data += "\n\t(No Authors)";
        data += "\n\n";

        return data;
    }
    public String showCatalogue() {
        ResultSet rs = query("""
SELECT book.ISBN, book.Title, book.Edition_No, book.NumOfCop, book.NumLeft, _author.Name, _author.Surname
FROM book
JOIN book_Author bookAuthor ON book.ISBN = bookAuthor.ISBN
JOIN author _author ON _author.AuthorId = bookAuthor.AuthorId;""");

        String data ="Show Catalogue: \n";
        Map<Integer, List<String>> info = new HashMap<>();
        try {
            while (rs.next()){

                int isbn = rs.getInt("ISBN");
                String title = rs.getString("Title");
                int edition_num = rs.getInt("Edition_No");
                int num_of_cop = rs.getInt("NumOfCop");
                int num_left = rs.getInt("NumLeft");
                String author_name = rs.getString("Name").replace(" ", "");
                author_name += " " + rs.getString("Surname").replace(" ", "");
                if (info.containsKey(isbn)){
                    info.get(isbn).add(author_name);
                }
                else{
                    info.put(isbn, new ArrayList<>());
                    info.get(isbn).add(Integer.toString(isbn));
                    info.get(isbn).add(title);
                    info.get(isbn).add(Integer.toString(edition_num));
                    info.get(isbn).add(Integer.toString(num_of_cop));
                    info.get(isbn).add(Integer.toString(num_left));
                    info.get(isbn).add(author_name);
                }
            }
        }
        catch (SQLException e){ throw new RuntimeException(e.getMessage()); }

        for (List<String> values : info.values()){
            data += values.get(0) + ": " + values.get(1) + "\n" +
                    "\tEdition: " + values.get(2) + "- Number of copies: " + values.get(3) + "- Copies left:" + values.get(4);
            if (values.size() > 5) {
                data += "\n\tAuthor/s: ";
                for (int i = 5; i < values.size(); i++) {
                    data += values.get(i);
                    if (i+1 < values.size()) data += ", ";
                }
            }
            else data += "\n\t(No Authors)";
            data += "\n\n";
        }
	    return data;
    }

    public String showLoanedBooks() {

	    return "Show Loaned Books Stub";
    }

    public String showAuthor(int authorID) {
	return "Show Author Stub";
    }

    public String showAllAuthors() {
	return "Show All Authors Stub";
    }

    public String showCustomer(int customerID) {
	return "Show Customer Stub";
    }

    public String showAllCustomers() {
	    return "Show All Customers Stub";
    }

    public String borrowBook(int isbn, int customerID, int day, int month, int year) {
	    return "Borrow Book Stub";
    }

    public String returnBook(int isbn, int customerid) {
	return "Return Book Stub";
    }

    public void closeDBConnection() {
    }

    public String deleteCus(int customerID) {
    	return "Delete Customer";
    }

    public String deleteAuthor(int authorID) {
    	return "Delete Author";
    }

    public String deleteBook(int isbn) {
    	return "Delete Book";
    }

    public ResultSet query(String sql){
        try {
            Statement s = con.createStatement();
            return s.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: \n" + e.getMessage());
        }
    }
}