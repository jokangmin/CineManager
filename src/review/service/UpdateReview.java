package review.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;
import cineManager.dao.ReviewDAO;

public class UpdateReview implements Review{
	private Scanner scan = new Scanner(System.in);
	private String userId;
	
	
	  public UpdateReview(String userId) {
	        this.userId = userId;
	    }
	
	   @Override
	   public void execute() {
		   
	      ReviewDAO reviewDAO = ReviewDAO.getInstance();
	      MovieDAO movieDAO = MovieDAO.getInstance();
	      System.out.print("리뷰 삭제할 영화 제목 : ");
	      String title = scan.nextLine();
	      
	      if(movieDAO.titleCheck(title, userId)) {
	    	  movieDAO.selectTitleSummary(title, userId);
	      }
	      else {
	         System.out.println("영화 제목 : " + title + " 이(가) 존재하지 않습니다.\n");
	         return;
	      }
	      System.out.print("리뷰 수정할 영화 등록번호 : ");
	      int code = scan.nextInt();
	      if(movieDAO.codeCheck(code) && movieDAO.isMovieOwnedByUser(code, userId)) {
	    	  boolean reviewExists = reviewDAO.checkReviewExists(code, userId);
	    	  if(reviewExists) {
	    		  String get_title = movieDAO.getTitle(code, userId);
	    		  System.out.print("수정리뷰 : ");
	    		  String review = scan.next();
	        	  reviewDAO.updateReview(code,userId,review);
	        	  System.out.println("영화\t" + get_title + " ( code : " + code +" )리뷰를 수정했습니다.\n");
	        	  return;
	    	  }
	    	  else {
	    		  System.out.println("후기가 작성되지 않은 영화이므로 수정이 불가합니다.");
	    		  return;
	    	  }
	      }
	      else {
	         System.out.println("올바른 code 값이 아닙니다.\n");
	         return;
	      }
	   }
    }