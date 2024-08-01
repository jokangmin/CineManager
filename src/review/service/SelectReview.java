package review.service;

import cineManager.dao.ReviewDAO;

public class SelectReview implements Review {
  
	private String userId;
	
	public SelectReview(String userId) {
        this.userId = userId;
    }
	@Override
	public void execute() {
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		reviewDAO.selectReview();
	}


}
	



