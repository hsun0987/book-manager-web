package com.example.bookmanagerweb.book;

import com.example.bookmanagerweb.repository.BookRepository;
import com.example.bookmanagerweb.repository.HashMapBook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class BM extends BookManager {
    private BookRepository books = new HashMapBook();
    static Scanner sc = new Scanner(System.in);

    @Override
    void init() {
        books.addBook(new Book(1L, "돈의 속성(300쇄 리커버에디션)", "김승호", Long.parseLong("9791188331796"), LocalDate.parse("2020-06-15")));
        books.addBook(new Book(2L,"K 배터리 레볼루션", "박순혁", Long.parseLong("9791191521221"), LocalDate.parse("2023-02-20")));
        books.addBook(new Book(3L, "위기의 역사", "오건영", Long.parseLong("9791169850360"), LocalDate.parse("2023-07-19")));
        books.addBook(new Book(4L, "위기의 역사", "오건영", Long.parseLong("9791169850360"), LocalDate.parse("2023-07-19")));
    }

    @Override
    void interactWithUser() throws IOException {
        System.out.println("■■■■■■ 최근 저장된 도서 목록 ■■■■■■");
        fileLoad();
        System.out.println(" ");
        backupThread thread = new backupThread();
        thread.setDaemon(true);
        thread.start();

        while (true) {
            System.out.println("■■■■■■ 도서 관리 프로그램 ■■■■■■");
            System.out.println("(1) 도서 조회");
            System.out.println("(2) 도서 등록");
            System.out.println("(3) 도서 수정");
            System.out.println("(4) 도서 삭제");
            System.out.println("(q) 프로그램 종료");
            System.out.print("선택 >> ");
            String userInput = sc.nextLine();


            switch (userInput) {
                case "1":
                    // 조회
                    System.out.println("1. 전체 조회");
                    System.out.println("2. 책 제목 조회");
                    System.out.println("3. 책 제목순 정렬");
                    System.out.println("4. 출간일 기간 조회");
                    System.out.println("5. 출간일 기간순 정렬");
                    System.out.println("6. 중복 책 찾기");
                    System.out.print("선택 >> ");
                    String op = sc.nextLine();

                    switch (op){
                        case "1":
                            printAllBook();
                            fileSave();
                            break;
                        case "2":
                            printBookName();
                            break;
                        case "3":
                            bookNameSort();
                            break;
                        case "4":
                            printPublishedDate();
                            break;
                        case "5":
                            dateSort();
                            break;
                        case "6":
                            dupBook();
                            break;
                    }
                    break;
                case "2":
                    // 등록
                    addBook();
                    fileSave();
                    break;
                case "3":
                    // 수정
                    updateBook();
                    fileSave();
                    break;
                case "4":
                    // 삭제
                    removeBook();
                    fileSave();
                    break;
                case "q":
                    // 메소드를 종료
                    System.out.println("프로그램 종료!");
                    return;
            }

        }
    }

    //(1) 도서 조회
    // 1.전체 조회
    @Override
    public void printAllBook() {
        for (Book book : books.getBooks()) {
            System.out.println(book);
        }
    }

    // 2.책 제목 조회
    public void printBookName(){
        System.out.print("책 제목 : ");
        String bookName = sc.nextLine();

        for (Book book : books.getBooks()) {
            if(bookName.equals(book.getName())) {
                System.out.println(book);
            }
        }
    }

    // 3.책 제목순 정렬
    class NameComparator implements Comparator<Book> {  // Comparator -> 함수형 인터페이스 !!
        @Override
        public int compare(Book o1, Book o2) {
            if (o1.getName().compareTo(o2.getName()) > 0) return 1;
            if (o1.getName().compareTo(o2.getName()) < 0) return -1;
            return 0;
        }
    }

    public void bookNameSort(){

        // key , value
        // hashMapBook.values() => list

        ArrayList<Book> sortedList = new ArrayList<>(books.getBooks());
        Collections.sort(sortedList, new NameComparator());

        sortedList.stream().forEach(book -> {
            System.out.println(book);
        });
    }

    // 4.출간일 기간 조회
    public void printPublishedDate(){
        System.out.print("조회 시작일 : ");
        String start = sc.nextLine();
        System.out.print("조회 종료일 : ");
        String end = sc.nextLine();

        for (Book book : books.getBooks()) {
            if(book.getPublishedDate().isAfter(LocalDate.parse(start))  &&  book.getPublishedDate().isBefore(LocalDate.parse(end))) {
                System.out.println(book);
            }
        }
    }

    // 5.출간일 기간순 정렬
    class DateComparator implements Comparator<Book> {
        @Override
        public int compare(Book o1, Book o2) {
            if (o1.getPublishedDate().isAfter(o2.getPublishedDate())) return 1;
            if (o1.getPublishedDate().isBefore(o2.getPublishedDate())) return -1;
            return 0;
        }
    }
    public void dateSort(){
        ArrayList<Book> sortedList = new ArrayList<>(books.getBooks());
        Collections.sort(sortedList, new DateComparator());

        sortedList.stream().forEach(book -> {
            System.out.println(book);
        });
    }

    // 6. 중복 책 찾기
    public void dupBook(){
        int count = 0;
        for (Book book : books.getBooks())  {
            for (Book book2 : books.getBooks()) {
                if (book.getId() == book2.getId()) continue;
                if (book.hashCode() == book2.hashCode()) {
                    if ((book.getName()).equals(book2.getName()) && (book.getAuthor()).equals(book2.getAuthor()) &&
                            (book.getIsbn()).equals(book2.getIsbn())){
                        System.out.println(book);
                        count ++;
                    }
                }
            }
        }
        System.out.println(count + "개의 책이 중복되었습니다.");
    }

    //(2) 도서 등록
    public void addBook() {
        try {
            System.out.println("등록 메서드 실행");
            // 1. 콘솔화면을 통해 사용자로부터 도서정보를 입력을 받는다.
            // id, 제목, 저자, isbn, 출판일 (5가지) (v)
            // 위의 정보로 책 객체를 생성한다. (v)
            // 2. 도서를 등록한다.
            // 사서를 통해 도서 저장 요청
            String[] bookInfo = new String[9];

            System.out.println("등록할 책의 종류를 선택해주세요.");
            System.out.println("(1)Book");
            System.out.println("(2)EBook");
            System.out.println("(3)AudioBook");
            System.out.print("선택 >> ");
            int bookType = Integer.parseInt(sc.nextLine());

            System.out.print("id: ");
            bookInfo[0] = sc.nextLine();
            System.out.print("제목: ");
            bookInfo[1] = sc.nextLine();
            System.out.print("저자: ");
            bookInfo[2] = sc.nextLine();
            System.out.print("isbn: ");
            bookInfo[3] = sc.nextLine();
            System.out.print("출판일(YYYY-MM-DD): ");
            bookInfo[4] = sc.nextLine();

            if (bookType == 1) {
                // book을 저장소에 저장
                Book book = new Book(Long.parseLong(bookInfo[0]),
                        bookInfo[1],
                        bookInfo[2],
                        Long.parseLong(bookInfo[3]),
                        LocalDate.parse(bookInfo[4]));
                books.addBook(book);
            } else if (bookType == 2) {
                // ebook을 저장소에 저장
                System.out.print("파일 크기: ");
                bookInfo[5] = sc.nextLine();
                Book eBook = new EBook(Long.parseLong(bookInfo[0]),
                        bookInfo[1],
                        bookInfo[2],
                        Long.parseLong(bookInfo[3]),
                        LocalDate.parse(bookInfo[4]),
                        bookInfo[5]);
                books.addBook(eBook);
            } else if (bookType == 3) {
                // audiobook을 저장소에 저장
                System.out.print("파일 크기: ");
                bookInfo[5] = sc.nextLine();
                System.out.print("언어: ");
                bookInfo[6] = sc.nextLine();
                System.out.print("실행시간: ");
                bookInfo[7] = sc.nextLine();

                Book audioBook = new AudioBook(Long.parseLong(bookInfo[0]),
                        bookInfo[1],
                        bookInfo[2],
                        Long.parseLong(bookInfo[3]),
                        LocalDate.parse(bookInfo[4]),
                        bookInfo[5],
                        bookInfo[6],
                        Integer.parseInt(bookInfo[7]));
                books.addBook(audioBook);
            }else
                System.out.print("책 종류에 해당하지 않습니다.");
        }catch (Exception e){
            System.out.println("예외가 발생했습니다.");
        }
    }

    //(3) 도서 수정
    @Override
    public void updateBook() {
        try{
            System.out.println("수정 메서드 실행");
            // 1. 수정할 도서를 찾는다. (사서는 알 수 있다.) (v)
            // 있으면 수정 가능
            // 없으면 수정 불가
            // 2. 수정할 도서가 있을 때:
            // 새로운 입력 값 : 사용자로부터 입력받는다.
            // 도서 정보(필드)를 바꾼다.

            System.out.print("수정할 도서번호를 입력해주세요: ");
            String id = sc.nextLine();
            Book book = findBook(Long.parseLong(id));

            // 책이 존재하지 않을 때
            if (book == null) {
                System.out.println("입력하신 책을 찾을 수 없습니다.");
                return;
            }
            // 책이 존재할 때
            String[] bookInfo = new String[9];
            bookInfo[0] = id;
            System.out.println("[수정 정보를 입력해주세요]");
            System.out.print("제목: ");
            bookInfo[1] = sc.nextLine();
            System.out.print("저자: ");
            bookInfo[2] = sc.nextLine();
            System.out.print("isbn: ");
            bookInfo[3] = sc.nextLine();
            System.out.print("출판일(YYYY-MM-DD): ");
            bookInfo[4] = sc.nextLine();

            book.setName(bookInfo[1]);
            book.setAuthor(bookInfo[2]);
            book.setIsbn(Long.parseLong(bookInfo[3]));
            book.setPublishedDate(LocalDate.parse(bookInfo[4]));
            if (book instanceof EBook){
                System.out.print("파일 크기: ");
                bookInfo[5] = sc.nextLine();

                ((EBook)book).setFilesize(bookInfo[5]);
            }
            if(book instanceof AudioBook){
                System.out.print("파일 크기: ");
                bookInfo[5] = sc.nextLine();
                System.out.print("언어: ");
                bookInfo[6] = sc.nextLine();
                System.out.print("실행시간: ");
                bookInfo[7] = sc.nextLine();

                ((AudioBook)book).setFilesize(bookInfo[5]);
                ((AudioBook)book).setLanguage(bookInfo[6]);
                ((AudioBook)book).setPlayTime(Integer.parseInt(bookInfo[7]));
            }
        }catch (Exception e){
            System.out.println("예외가 발생했습니다.");
        }
    }

    //(4) 도서 삭제
    @Override
    public void removeBook() {
        System.out.println("삭제 메서드 실행");
        // 1. 삭제할 도서를 찾는다.
        // 없으면 삭제 불가
        // 있으면 삭제 가능
        // 2. 삭제할 도서가 있다면
        // 사서한테 도서 삭제 요청
        System.out.print("삭제할 도서번호를 입력해주세요: ");
        String id = sc.nextLine();

        Book book = findBook(Long.parseLong(id));
        if (book == null) {
            System.out.println("입력하신 책을 찾을 수 없습니다.");
        }
        books.removeBook(book);
    }

    public Book findBook(long id) {
        for (Book book : books.getBooks()) {
            if (id == book.getId()) {
                return book;
            }
        }
        // bookList를 다 돌았는데 해당 id의 도서를 못찾았다.
        return null;
    }

    public void fileSave() {     // output 출력
        try {
            File list = new File("src/base/books.txt");
            FileWriter writer = new FileWriter(list);

            List<Book> bookList = books.getBooks();
            for (Book book : bookList){
                writer.write(book.toString());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.print("입출력 오류");
        }
    }

    public void fileLoad() {     // input 입력
        try {
            File list = new File("src/base/books.txt");
            FileReader reader = new FileReader(list, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);

            String str;
            while ((str = br.readLine()) != null){
                String[] str2 = str.split(",");
                System.out.println(str);

                if (str2.length == 5)
                    books.addBook(new Book(Long.parseLong(str2[0].trim()),  str2[1],  str2[2],  Long.parseLong(str2[3].trim()),  LocalDate.parse(str2[4].trim())));
                else if (str2.length == 6)
                    books.addBook(new EBook(Long.parseLong(str2[0].trim()),  str2[1],  str2[2],  Long.parseLong(str2[3].trim()),  LocalDate.parse(str2[4].trim()), str2[5]));
                else
                    books.addBook(new AudioBook(Long.parseLong(str2[0].trim()),  str2[1],  str2[2],  Long.parseLong(str2[3].trim()),  LocalDate.parse(str2[4].trim()), str2[5], str2[6], Integer.parseInt(str2[7])));

            }
            reader.close();

        }catch (IOException e){
            System.out.print("입출력 오류");
        }
    }

    public static void fileBackup(){
        File file = new File("src/base/books.txt");
        File copy = new File("src/base/booksBackup.txt");

        try {
            int c;
            FileReader fr = new FileReader(file);
            FileWriter fw = new FileWriter(copy);
            while ((c = fr.read()) != -1){
                fw.write((char) c);
            }
            fr.close();
            fw.close();

        }catch (IOException e){
            System.out.println("파일 복사 오류");
        }
    }

    public static class backupThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try{
                    Thread.sleep(10000);
                    fileBackup();
                }catch (InterruptedException e){
                    return;
                }
            }
        }
    }
}