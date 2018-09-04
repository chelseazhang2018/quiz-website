package support;

public class Review {
	private String review;
	private String reviewer;
	private String quizTitle;
	private String reviewTime;
	public Review(String quizTitle, String reviewer, String review, String reviewTime) {
		this.quizTitle = quizTitle;
		this.reviewer = reviewer;
		this.review = review;
		this.reviewTime = reviewTime;
	}
	public String getReview() {
		return review;
	}
	public String getReviewer() {
		return reviewer;
	}
	public String getQuizTitle() {
		return quizTitle;
	}
	public String getReviewTime() {
		return reviewTime;
	}
}
