package com.gameinn.review.service;

import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.dto.ReviewPageDTO;
import com.gameinn.review.service.dto.ReviewReadDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.feignClient.GameService;
import com.gameinn.review.service.feignClient.UserService;
import com.gameinn.review.service.model.Game;
import com.gameinn.review.service.model.User;
import com.gameinn.review.service.repository.ReviewRepository;
import com.gameinn.review.service.service.ReviewRESTService;
import com.gameinn.review.service.util.ReviewObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ReviewServiceApplicationTests {
	@InjectMocks
	private ReviewRESTService reviewRESTService;
	@Mock
	private GameService gameService;
	@Mock
	private UserService userService;
	@Mock
	private ReviewRepository reviewRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void addReviewTest() {
		ReviewCreateUpdateDTO reviewCreateUpdateDTO = new ReviewCreateUpdateDTO();
		reviewCreateUpdateDTO.setGameId("mockGameId");
		reviewCreateUpdateDTO.setVote(5);
		reviewCreateUpdateDTO.setVoted(true);
		reviewCreateUpdateDTO.setContext("mockContext");
		reviewCreateUpdateDTO.setUserId("mockUserId");

		Review dbReview = ReviewObjectMapper.toEntity(reviewCreateUpdateDTO);
		dbReview.setId("mockDbId");

		Mockito.when(reviewRepository.insert(any(Review.class))).thenReturn(dbReview);
		Mockito.when(gameService.updateGameVote("mockGameId",new Game())).thenReturn(new Game());

		Review result = reviewRESTService.addReview(reviewCreateUpdateDTO);

		Assertions.assertEquals(reviewCreateUpdateDTO.getGameId(),result.getGameId());
		Assertions.assertEquals(reviewCreateUpdateDTO.getUserId(),result.getUserId());
		Assertions.assertEquals(reviewCreateUpdateDTO.getContext(),result.getContext());
		Assertions.assertEquals(reviewCreateUpdateDTO.getVote(),result.getVote());
	}

	@Test
	void getAllReviewsTest(){
		List<Review> reviews = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Review review = new Review();
			review.setId(Integer.toString(i));
			review.setUserId("user"+ i);
			review.setGameId("game"+ i);
			review.setContext("mockContext");
			review.setVote(3);
			review.setVoted(true);
			review.setLikeCount(i);
			review.setLikedUsers(null);
			review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
			review.setUpdatedAt(0);
			review.setCreatedAt(0);
			reviews.add(review);
		}

		Mockito.when(reviewRepository.findAll()).thenReturn(reviews);

		List<Review> result = reviewRESTService.getAllReviews();

		Assertions.assertEquals(result.size(),reviews.size());
	}
	@Test
	void getAllReviewsEmptyTest(){
		Mockito.when(reviewRepository.findAll()).thenReturn(new ArrayList<>());
		List<Review> result = reviewRESTService.getAllReviews();
		Assertions.assertEquals(result.size(),0);
	}

	@Test
	void getReviewsByGameIdTest(){
		List<Review> reviews = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Review review = new Review();
			review.setId(Integer.toString(i));
			review.setUserId("user"+ i);
			review.setGameId("mockGameId");
			review.setContext("mockContext");
			review.setVote(3);
			review.setVoted(true);
			review.setLikeCount(10-i);
			review.setLikedUsers(null);
			review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
			review.setUpdatedAt(0);
			review.setCreatedAt(0);
			reviews.add(review);
		}
		Mockito.when(reviewRepository.getReviewsByGameIdOrderByLikeCountDesc("mockGameId")).thenReturn(reviews);

		List<Review> result = reviewRESTService.getReviewsByGameId("mockGameId");

		Assertions.assertEquals(result.size(),reviews.size());
		Assertions.assertTrue(result.get(0).getLikeCount() > result.get(1).getLikeCount());
		Assertions.assertEquals("mockGameId",result.get(5).getGameId());
	}

	@Test
	void getReviewsByGameIdInvalidTest(){
		Mockito.when(reviewRepository.getReviewsByGameIdOrderByLikeCountDesc("mockGameId")).thenReturn(new ArrayList<>());
		List<Review> result = reviewRESTService.getReviewsByGameId("mockGameId");
		Assertions.assertEquals(result.size(),0);
	}

	@Test
	void getReviewsByUserIdTest(){
		List<Review> reviews = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Review review = new Review();
			review.setId(Integer.toString(i));
			review.setUserId("mockUserId");
			review.setGameId("game" + i);
			review.setContext("mockContext");
			review.setVote(3);
			review.setVoted(true);
			review.setLikeCount(10-i);
			review.setLikedUsers(null);
			review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
			review.setUpdatedAt(0);
			review.setCreatedAt(0);
			reviews.add(review);
		}
		Mockito.when(reviewRepository.getReviewsByUserIdOrderByLikeCountDesc("mockUserId")).thenReturn(reviews);

		List<Review> result = reviewRESTService.getReviewsByUserId("mockUserId");

		Assertions.assertEquals(result.size(),reviews.size());
		Assertions.assertTrue(result.get(0).getLikeCount() > result.get(1).getLikeCount());
		Assertions.assertEquals("mockUserId",result.get(5).getUserId());
	}

	@Test
	void getReviewsByUserIdInvalidTest(){
		Mockito.when(reviewRepository.getReviewsByGameIdOrderByLikeCountDesc("mockUserId")).thenReturn(new ArrayList<>());
		List<Review> result = reviewRESTService.getReviewsByGameId("mockUserId");
		Assertions.assertEquals(result.size(),0);
	}

	@Test
	void getReviewsByGameIdandUserIdTest(){
		List<Review> reviews = new ArrayList<>();
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);
		reviews.add(review);

		Mockito.when(reviewRepository.getReviewsByUserIdAndGameIdOrderByLikeCountDesc("mockUserId","mockGameId")).thenReturn(reviews);

		List<Review> result = reviewRESTService.getReviewsByUserIdAndGameId("mockUserId","mockGameId");

		Assertions.assertEquals(result.size(),reviews.size());
		Assertions.assertEquals("mockGameId",result.get(0).getGameId());
		Assertions.assertEquals("mockUserId",result.get(0).getUserId());
	}

	@Test
	void getReviewsByGameIdandUserIdInvalidTest(){
		Mockito.when(reviewRepository.getReviewsByUserIdAndGameIdOrderByLikeCountDesc("mockUserId","mockGameId")).thenReturn(new ArrayList<>());
		List<Review> result = reviewRESTService.getReviewsByUserIdAndGameId("mockUserId","mockGameId");
		Assertions.assertEquals(result.size(),0);
	}

	@Test
	void likeReviewTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(new ArrayList<>());
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));
		try{
			reviewRESTService.likeReview("userId","mockId");
		}catch (Exception e){
			Assertions.assertEquals(e.getMessage(),"There is no review with given id: "+review.getId());
		}
	}

	@Test
	void likeReviewNullListTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));
		try{
			reviewRESTService.likeReview("userId","mockId");
		}catch (Exception e){
			Assertions.assertEquals(e.getMessage(),"There is no review with given id: "+review.getId());
		}
	}

	@Test
	void likeInvalidReviewTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.empty());
		try{
			reviewRESTService.likeReview("mockUserId","mockId");
		}catch (Exception e){
			Assertions.assertEquals(e.getMessage(),"There is no review with given id: "+review.getId());
		}
	}

	@Test
	void unlikeReviewTest(){
		List<String> likedUsers = new ArrayList<>();
		likedUsers.add("userId");
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(1);
		review.setLikedUsers(likedUsers);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));
		try{
			reviewRESTService.unlikeReview("userId","mockId");
		}catch (Exception e){
			Assertions.assertEquals(e.getMessage(),"There is no review with given id: "+ review.getId());
		}
	}

	@Test
	void unlikeInvalidReviewTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.empty());
		try{
			reviewRESTService.likeReview("mockUserId","mockId");
		}catch (Exception e){
			Assertions.assertEquals(e.getMessage(),"There is no review with given id: "+review.getId());
		}
	}

	@Test
	void unlikeReviewNullListTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(1);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));
		try{
			reviewRESTService.unlikeReview("userId","mockId");
		}catch (Exception e){
			Assertions.assertEquals("Empty liked users list",e.getMessage());
		}
	}

	@Test
	void unlikeReviewEmptyListTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(1);
		review.setLikedUsers(new ArrayList<>());
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));
		try{
			reviewRESTService.unlikeReview("userId","mockId");
		}catch (Exception e){
			Assertions.assertEquals("Empty liked users list",e.getMessage());
		}
	}

	@Test
	void unlikeReviewInvalidUserTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(1);
		review.setLikedUsers(Arrays.asList("id1","id2"));
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));
		try{
			reviewRESTService.unlikeReview("userId","mockId");
		}catch (Exception e){
			Assertions.assertEquals("The user did not like the review before",e.getMessage());
		}
	}

	@Test
	void getReviewsByUserIdAsReviewReadDTOTest(){

		List<Game> games = new ArrayList<>();
		List<ReviewReadDTO> expected = new ArrayList<>();
		expected.add(new ReviewReadDTO());
		expected.add(new ReviewReadDTO());
		for(int i = 0; i < 2; i++){
			Game game = new Game();
			game.setId("game" +i);
			game.setCover("mockCover");
			game.setVote(4.5f);
			game.setName("MOCKGAME0");
			game.setGenres(null);
			game.setFirst_release_date(0);
			game.setPlatforms(null);
			game.setPublisher("mockPublisher");
			game.setSummary("MockSummary");
			game.setVoteCount(10);
			games.add(game);
			expected.get(i).setGame(game);
		}

		List<Review> reviews = new ArrayList<>();
		for(int i = 0; i < 2; i++){
			Review review = new Review();
			review.setId(Integer.toString(i));
			review.setUserId("userId");
			review.setGameId("game" + i);
			review.setContext("mockContext");
			review.setVote(3);
			review.setVoted(true);
			review.setLikeCount(10-i);
			review.setLikedUsers(null);
			review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
			review.setUpdatedAt(0);
			review.setCreatedAt(0);
			reviews.add(review);
			expected.get(i).setReview(review);
		}

		Mockito.when(reviewRepository.getReviewsByUserIdOrderByLikeCountDesc("userId")).thenReturn(reviews);
		Mockito.when(gameService.getAllGamesByGameId(Arrays.asList("game0", "game1"))).thenReturn(games);

		List<ReviewReadDTO> result = reviewRESTService.getReviewsByUserIdAsReviewReadDTO("userId");

		Assertions.assertEquals(expected.size(),result.size());
		Assertions.assertEquals(expected,result);
	}

	@Test
	void getReviewPageTest(){
		User user0 = new User();
		user0.setId("user0");
		user0.setUsername("mockUsername");
		user0.setFollowing(new ArrayList<>());
		user0.getFollowing().add("user1");
		user0.getFollowing().add("user2");
		user0.setProfileImage("mockImage");

		User user1 = new User();
		user1.setId("user1");
		user1.setUsername("mockUsername");
		user1.setFollowing(new ArrayList<>());
		user1.getFollowing().add("user0");
		user1.getFollowing().add("user2");
		user1.setProfileImage("mockImage");

		User user2 = new User();
		user2.setId("user2");
		user2.setUsername("mockUsername");
		user2.setFollowing(new ArrayList<>());
		user2.getFollowing().add("user1");
		user2.getFollowing().add("user");
		user2.setProfileImage("mockImage");

		Game game0 = new Game();
		game0.setId("game0");
		game0.setCover("mockCover");
		game0.setVote(4.5f);
		game0.setName("MOCKGAME0");
		game0.setGenres(null);
		game0.setFirst_release_date(0);
		game0.setPlatforms(null);
		game0.setPublisher("mockPublisher");
		game0.setSummary("MockSummary");
		game0.setVoteCount(10);

		Game game1 = new Game();
		game1.setId("game1");
		game1.setCover("mockCover");
		game1.setVote(4.5f);
		game1.setName("MOCKGAME1");
		game1.setGenres(null);
		game1.setFirst_release_date(0);
		game1.setPlatforms(null);
		game1.setPublisher("mockPublisher");
		game1.setSummary("MockSummary");
		game1.setVoteCount(10);

		List<Review> reviews = new ArrayList<>();
		List<ReviewReadDTO> friendReviewsResult = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Review review = new Review();
			review.setId(Integer.toString(i));
			review.setUserId("user" + i%3);
			review.setGameId("game" + i%2);
			review.setContext("mockContext");
			review.setVote(3);
			review.setVoted(true);
			review.setLikeCount(10-i);
			review.setLikedUsers(null);
			review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
			review.setUpdatedAt(0);
			review.setCreatedAt(0);
			reviews.add(review);
			if(i%3 != 0 && friendReviewsResult.size() != 5){
				ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
				reviewReadDTO.setReview(review);
				switch (i%3){
					case 1:
						reviewReadDTO.setUser(user1);
						break;
					case 2:
						reviewReadDTO.setUser(user2);
						break;
				}
				switch (i%2){
					case 0:
						reviewReadDTO.setGame(game0);
						break;
					case 1:
						reviewReadDTO.setGame(game1);
						break;
				}
				friendReviewsResult.add(reviewReadDTO);
			}
		}

		List<ReviewReadDTO> mostPopularReviewsResult = new ArrayList<>();
		ReviewReadDTO reviewReadDTO1 = new ReviewReadDTO();
		reviewReadDTO1.setReview(reviews.get(0));
		reviewReadDTO1.setUser(user0);
		reviewReadDTO1.setGame(game0);
		ReviewReadDTO reviewReadDTO2 = new ReviewReadDTO();
		reviewReadDTO2.setReview(reviews.get(1));
		reviewReadDTO2.setUser(user1);
		reviewReadDTO2.setGame(game1);
		ReviewReadDTO reviewReadDTO3 = new ReviewReadDTO();
		reviewReadDTO3.setReview(reviews.get(2));
		reviewReadDTO3.setUser(user2);
		reviewReadDTO3.setGame(game0);
		ReviewReadDTO reviewReadDTO4 = new ReviewReadDTO();
		reviewReadDTO4.setReview(reviews.get(3));
		reviewReadDTO4.setUser(user0);
		reviewReadDTO4.setGame(game1);
		ReviewReadDTO reviewReadDTO5 = new ReviewReadDTO();
		reviewReadDTO5.setReview(reviews.get(4));
		reviewReadDTO5.setUser(user1);
		reviewReadDTO5.setGame(game0);
		mostPopularReviewsResult.add(reviewReadDTO1);
		mostPopularReviewsResult.add(reviewReadDTO2);
		mostPopularReviewsResult.add(reviewReadDTO3);
		mostPopularReviewsResult.add(reviewReadDTO4);
		mostPopularReviewsResult.add(reviewReadDTO5);

		Mockito.when(userService.getUserById("user0")).thenReturn(user0);
		Mockito.when(userService.getUserById("user1")).thenReturn(user1);
		Mockito.when(userService.getUserById("user2")).thenReturn(user2);
		Mockito.when(gameService.getGame("game0")).thenReturn(game0);
		Mockito.when(gameService.getGame("game1")).thenReturn(game1);
		Mockito.when(reviewRepository.findAll()).thenReturn(reviews);

		try{
			ReviewPageDTO result = reviewRESTService.getReviewPage("user0");

			Assertions.assertEquals(user0,result.getMostPopularReviews().get(0).getUser());
			Assertions.assertEquals(5,result.getFriendReviews().size());
			Assertions.assertEquals(friendReviewsResult,result.getFriendReviews());
			Assertions.assertEquals(mostPopularReviewsResult,result.getMostPopularReviews());
		}catch (Exception e) {
			Assertions.assertEquals("",e.getMessage());
		}
	}

	@Test
	void updateReviewTest(){

	}

	@Test
	void deleteReviewTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.of(review));

		try{
			Review result = reviewRESTService.deleteReview("mockId");
			Assertions.assertEquals(review,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}

	@Test
	void deleteInvalidReviewTest(){
		Review review = new Review();
		review.setId("mockId");
		review.setUserId("mockUserId");
		review.setGameId("mockGameId");
		review.setContext("mockContext");
		review.setVote(3);
		review.setVoted(true);
		review.setLikeCount(8);
		review.setLikedUsers(null);
		review.setDuplicateCheckVariable(review.getUserId()+review.getGameId());
		review.setUpdatedAt(0);
		review.setCreatedAt(0);

		Mockito.when(reviewRepository.findById("mockId")).thenReturn(Optional.empty());

		try{
			reviewRESTService.deleteReview("mockId");
		}catch (Exception e){
			Assertions.assertEquals("There is no review with given id: "+review.getId(),e.getMessage());
		}
	}
}
