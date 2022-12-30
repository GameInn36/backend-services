package com.gameinn.game.service;

import com.gameinn.game.service.dto.DisplayGamesDTO;
import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.dto.GameLogDTO;
import com.gameinn.game.service.dto.GamePageDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.feignClient.ReviewService;
import com.gameinn.game.service.feignClient.UserService;
import com.gameinn.game.service.model.GameLog;
import com.gameinn.game.service.model.GamePageReview;
import com.gameinn.game.service.model.Review;
import com.gameinn.game.service.model.User;
import com.gameinn.game.service.repository.GameRepository;
import com.gameinn.game.service.service.GameRESTService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class GameServiceApplicationTests {

	@InjectMocks
	private GameRESTService gameRESTService;
	@Mock
	private UserService userService;
	@Mock
	private ReviewService reviewService;
	@Mock
	private GameRepository gameRepository;

	@Test
	void addGameTest(){
		Game addedGame = new Game.GameBuilder("game")
				.setId("gameId")
				.build();

		Game addGame= new Game.GameBuilder("game")
				.build();
		GameDTO gameDTO = new GameDTO();
		gameDTO.setName("game");

		Mockito.when(gameRepository.insert(addGame)).thenReturn(addedGame);
		Game result = gameRESTService.addGame(gameDTO);
		Assertions.assertEquals(addedGame,result);
	}
	@Test
	void getGameTest() {
		Game game = new Game.GameBuilder("game")
				.setId("id")
				.build();
		Mockito.when(gameRepository.findById("id")).thenReturn(Optional.of(game));
		try {
			Game result = gameRESTService.getGame("id");
			Assertions.assertEquals(game,result);
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: " + game.getId(),e.getMessage());
		}
	}

	@Test
	void getInvalidGameTest(){
		Mockito.when(gameRepository.findById("id")).thenReturn(Optional.empty());
		try {
			Game result = gameRESTService.getGame("id");
			Assertions.assertEquals(new Game(),result);
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: id",e.getMessage());
		}
	}

	@Test
	void getAllGamesTest(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			games.add(new Game.GameBuilder("game"+i).setId("g"+i).build());
		}

		Mockito.when(gameRepository.findAll()).thenReturn(games);

		List<Game> result = gameRESTService.getAllGames();
		Assertions.assertEquals(games,result);
	}

	@Test
	void getAllGamesByIdTest(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			games.add(new Game.GameBuilder("game"+i).setId("g"+i).build());
		}

		Mockito.when(gameRepository.findAllById(games.stream().map(Game::getId).collect(Collectors.toList()))).thenReturn(games);

		List<Game> result = gameRESTService.getAllGamesById(games.stream().map(Game::getId).collect(Collectors.toList()));
		Assertions.assertEquals(games,result);
	}

	@Test
	void updateGameTest(){
		Game oldGame = new Game.GameBuilder("game")
				.setId("gameId")
				.setCover("oldcover")
				.build();

		Game expectedGame = new Game.GameBuilder("game2")
				.setId("gameId")
				.setCover("newcover")
				.build();

		GameDTO gameDTO = new GameDTO();
		gameDTO.setName("game2");
		gameDTO.setCover("newcover");

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.ofNullable(oldGame));
		Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
		try{
			Game result = gameRESTService.updateGame("gameId",gameDTO);
			Assertions.assertEquals(expectedGame,result);
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}

	@Test
	void updateInvalidGameTest(){
		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.empty());
		try{
			gameRESTService.updateGame("gameId",new GameDTO());
		}catch (Exception e){
			Assertions.assertEquals("Game not found with given id: gameId",e.getMessage());
		}
	}

	@Test
	void getGamesByPublisherTest(){
		List<Game> games = new ArrayList<>();
		for (int i = 0; i < 5; i++){
			games.add(new Game.GameBuilder("game"+i).setId("id"+i).setPublisher("publisher").build());
		}

		Mockito.when(gameRepository.findByPublisherStartingWithIgnoreCaseOrderByNameAsc("publisher")).thenReturn(games);

		List<Game> result = gameRESTService.getGamesByPublisher("publisher");
		Assertions.assertEquals(games,result);
	}

	@Test
	void getGamesByPlatformTest(){
		List<Game> games = new ArrayList<>();
		for (int i = 0; i < 5; i++){
			games.add(new Game.GameBuilder("game"+i).setId("id"+i).setPublisher("publisher").setPlatforms(Arrays.asList("p1","p2")).build());
		}

		Mockito.when(gameRepository.findByPlatformsContainingIgnoreCase(new String[] {"p1","p2"})).thenReturn(games);

		List<Game> result = gameRESTService.getGamesByPlatform(new String[] {"p1","p2"});
		Assertions.assertEquals(games,result);
	}

	@Test
	void getGamesByNameTest(){
		List<Game> games = new ArrayList<>();
		for (int i = 0; i < 5; i++){
			games.add(new Game.GameBuilder("game"+i).setId("id"+i).setPublisher("publisher").build());
		}

		Mockito.when(gameRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc("game")).thenReturn(games);

		List<Game> result = gameRESTService.getGamesByName("game");
		Assertions.assertEquals(games,result);
	}

	@Test
	void updateVotePositiveTest(){
		final int VOTE = 3;
		Game game = new Game.GameBuilder("game")
				.setId("gameId")
				.setVote(0)
				.setVoteCount(0)
				.build();

		Game expected = new Game.GameBuilder("game")
				.setId("gameId")
				.setVote(VOTE)
				.setVoteCount(1)
				.build();

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.ofNullable(game));
		Mockito.when(gameRepository.save(expected)).thenReturn(expected);

		try{
			Game result = gameRESTService.updateVote("gameId",VOTE);
			Assertions.assertEquals(expected.getVote(),result.getVote());
			assert game != null;
			Assertions.assertEquals(expected.getVoteCount(),result.getVoteCount());
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}

	@Test
	void updateVoteNegativeTest(){
		final int VOTE = -3;
		Game game = new Game.GameBuilder("game")
				.setId("gameId")
				.setVote(-VOTE)
				.setVoteCount(1)
				.build();

		Game expected = new Game.GameBuilder("game")
				.setId("gameId")
				.setVote(0)
				.setVoteCount(0)
				.build();

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.ofNullable(game));
		Mockito.when(gameRepository.save(expected)).thenReturn(expected);

		try{
			Game result = gameRESTService.updateVote("gameId",VOTE);
			Assertions.assertEquals(expected.getVote(),result.getVote());
			assert game != null;
			Assertions.assertEquals(expected.getVoteCount(),result.getVoteCount());
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}
	@Test
	void updateVoteInvalidGameTest(){
		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.empty());
		try{
			gameRESTService.updateVote("gameId",5);
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}

	@Test
	void increaseLogCountTest(){
		Game oldGame = new Game.GameBuilder("name")
				.setId("gameId")
				.build();

		Game newGame = new Game.GameBuilder("name")
				.setId("gameId")
				.setLogCount(1)
				.build();

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.ofNullable(oldGame));
		Mockito.when(gameRepository.save(newGame)).thenReturn(newGame);

		try{
			Game result = gameRESTService.increaseLogCount("gameId");
			Assertions.assertEquals(newGame,result);
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}

	@Test
	void increaseLogCountInvalidGameTest(){
		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.empty());
		try{
			gameRESTService.increaseLogCount("gameId");
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}

	@Test
	void getGamePageTest(){
		Game game = new Game.GameBuilder("game")
				.setId("gameId")
				.build();
		List<User> users = new ArrayList<>();
		List<Review> reviews = new ArrayList<>();
		for (int i = 0; i < 4; i++){
			User user = new User();
			user.setId("u"+i);
			user.setUsername("user"+i);
			users.add(user);

			Review review = new Review();
			review.setId("r"+i);
			review.setUserId("u"+i);
			review.setContext("context"+i);
			reviews.add(review);
		}

		User requestOwner = new User();
		requestOwner.setUsername("username");
		requestOwner.setId("requestOwnerId");
		requestOwner.setFollowing(users.stream().map(User::getId).collect(Collectors.toList()));
		requestOwner.getFollowing().remove("u3");

		GamePageDTO expected = new GamePageDTO();
		expected.setGame(game);

		List<GamePageReview> allGameReviews = new ArrayList<>();
		for (int i = 0; i < reviews.size(); i++){
			GamePageReview gamePageReview = new GamePageReview.Builder()
					.setId(reviews.get(i).getId())
					.setVoted(reviews.get(i).isVoted())
					.setVote(reviews.get(i).getVote())
					.setLikeCount(reviews.get(i).getLikeCount())
					.setContext(reviews.get(i).getContext())
					.setUpdatedAt(reviews.get(i).getUpdatedAt())
					.setCreatedAt(reviews.get(i).getCreatedAt())
					.setUser(users.get(i))
					.build();
			allGameReviews.add(gamePageReview);
		}
		expected.setReviews(allGameReviews);
		expected.setFollowedFriendsReviews(expected.getReviews().stream().filter((gamePageReview -> requestOwner.getFollowing().contains(gamePageReview.getUser().getId()))).collect(Collectors.toList()));

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.of(game));
		Mockito.when(reviewService.getReviewsByGameId("gameId")).thenReturn(reviews);
		Mockito.when(userService.getAllUsers(reviews.stream().map(Review::getUserId).collect(Collectors.toList()))).thenReturn(users);
		Mockito.when(userService.getUserById("requestOwnerId")).thenReturn(requestOwner);
		try{
			GamePageDTO result = gameRESTService.getGamePage("gameId","requestOwnerId");
			Assertions.assertEquals(expected,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}

	@Test
	void getGamePageNoReviewTest(){
		Game game = new Game.GameBuilder("game")
				.setId("gameId")
				.build();
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 4; i++){
			User user = new User();
			user.setId("u"+i);
			user.setUsername("user"+i);
			users.add(user);
		}

		User requestOwner = new User();
		requestOwner.setUsername("username");
		requestOwner.setId("requestOwnerId");
		requestOwner.setFollowing(users.stream().map(User::getId).collect(Collectors.toList()));
		requestOwner.getFollowing().remove("user3");

		GamePageDTO expected = new GamePageDTO();
		expected.setGame(game);

		List<GamePageReview> allGameReviews = new ArrayList<>();
		expected.setReviews(allGameReviews);
		expected.setFollowedFriendsReviews(expected.getReviews().stream().filter((gamePageReview -> requestOwner.getFollowing().contains(gamePageReview.getUser().getId()))).collect(Collectors.toList()));

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.of(game));
		Mockito.when(reviewService.getReviewsByGameId("gameId")).thenReturn(new ArrayList<>());
		Mockito.when(userService.getUserById("requestOwnerId")).thenReturn(requestOwner);
		try{
			GamePageDTO result = gameRESTService.getGamePage("gameId","requestOwnerId");
			Assertions.assertEquals(expected,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}

	@Test
	void getGamePageNoFriendTest(){
		Game game = new Game.GameBuilder("game")
				.setId("gameId")
				.build();
		List<User> users = new ArrayList<>();
		List<Review> reviews = new ArrayList<>();
		for (int i = 0; i < 4; i++){
			User user = new User();
			user.setId("u"+i);
			user.setUsername("user"+i);
			users.add(user);

			Review review = new Review();
			review.setId("r"+i);
			review.setUserId("u"+i);
			review.setContext("context"+i);
			reviews.add(review);
		}

		User requestOwner = new User();
		requestOwner.setUsername("username");
		requestOwner.setId("requestOwnerId");

		GamePageDTO expected = new GamePageDTO();
		expected.setGame(game);

		List<GamePageReview> allGameReviews = new ArrayList<>();
		for (int i = 0; i < reviews.size(); i++){
			GamePageReview gamePageReview = new GamePageReview.Builder()
					.setId(reviews.get(i).getId())
					.setVoted(reviews.get(i).isVoted())
					.setVote(reviews.get(i).getVote())
					.setLikeCount(reviews.get(i).getLikeCount())
					.setContext(reviews.get(i).getContext())
					.setUpdatedAt(reviews.get(i).getUpdatedAt())
					.setCreatedAt(reviews.get(i).getCreatedAt())
					.setUser(users.get(i))
					.build();
			allGameReviews.add(gamePageReview);
		}
		expected.setReviews(allGameReviews);
		expected.setFollowedFriendsReviews(new ArrayList<>());

		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.of(game));
		Mockito.when(reviewService.getReviewsByGameId("gameId")).thenReturn(reviews);
		Mockito.when(userService.getAllUsers(reviews.stream().map(Review::getUserId).collect(Collectors.toList()))).thenReturn(users);
		Mockito.when(userService.getUserById("requestOwnerId")).thenReturn(requestOwner);
		try{
			GamePageDTO result = gameRESTService.getGamePage("gameId","requestOwnerId");
			Assertions.assertEquals(expected,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}
	@Test
	void getGamePageInvalidGameTest(){
		Mockito.when(gameRepository.findById("gameId")).thenReturn(Optional.empty());
		try{
			gameRESTService.getGamePage("gameId","userId");
		}catch (Exception e){
			Assertions.assertEquals("There is no game with given id: gameId",e.getMessage());
		}
	}

	@Test
	void getDisplayGamesPageTest(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			games.add(new Game.GameBuilder("game"+i)
					.setId("g"+i)
					.setFirst_release_date(10-i)
					.setVote(10-i)
					.build());
		}

		List<GameLogDTO> gameLogDTOS = new ArrayList<>();
		List<User> following = new ArrayList<>();
		for(int i = 0; i < 3; i++){
			User user = new User();
			user.setId("u"+i);
			user.setUsername("user"+i);
			user.setLogs(new ArrayList<>());

			GameLog gameLog = new GameLog();
			gameLog.setGameId("g0");
			gameLog.setCreateDate(1L);
			user.getLogs().add(gameLog);
			following.add(user);

			GameLogDTO gameLogDTO = new GameLogDTO();
			gameLogDTO.setLog(gameLog);
			gameLogDTO.setUser(user);
			gameLogDTO.setGame(games.get(0));
			gameLogDTOS.add(gameLogDTO);
		}
		User requestOwner = new User();
		requestOwner.setId("requestOwnerId");
		requestOwner.setUsername("requestOwner");
		requestOwner.setFollowing(following.stream().map(User::getId).collect(Collectors.toList()));

		DisplayGamesDTO expected = new DisplayGamesDTO();
		expected.setNewGames(games.subList(0,5));
		expected.setMostPopularGames(games.subList(0,5));
		expected.setNewsFromFriends(gameLogDTOS);

		Mockito.when(gameRepository.findAll()).thenReturn(games);
		Mockito.when(userService.getUserById("requestOwnerId")).thenReturn(requestOwner);
		Mockito.when(userService.getAllUsers(following.stream().map(User::getId).collect(Collectors.toList()))).thenReturn(following);
		Mockito.when(gameRepository.findById("g0")).thenReturn(Optional.ofNullable(games.get(0)));

		try {
			DisplayGamesDTO result = gameRESTService.getDisplayGamesPage("requestOwnerId");
			Assertions.assertEquals(expected,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}

	@Test
	void getDisplayGamesPageNoFollowingTest(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			games.add(new Game.GameBuilder("game"+i)
					.setId("g"+i)
					.setFirst_release_date(10-i)
					.setVote(10-i)
					.build());
		}

		List<GameLogDTO> gameLogDTOS = new ArrayList<>();
		User requestOwner = new User();
		requestOwner.setId("requestOwnerId");
		requestOwner.setUsername("requestOwner");

		DisplayGamesDTO expected = new DisplayGamesDTO();
		expected.setNewGames(games.subList(0,5));
		expected.setMostPopularGames(games.subList(0,5));
		expected.setNewsFromFriends(gameLogDTOS);

		Mockito.when(gameRepository.findAll()).thenReturn(games);
		Mockito.when(userService.getUserById("requestOwnerId")).thenReturn(requestOwner);

		try {
			DisplayGamesDTO result = gameRESTService.getDisplayGamesPage("requestOwnerId");
			Assertions.assertEquals(expected,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}

	@Test
	void getDisplayGamesPageNoLogsTest(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			games.add(new Game.GameBuilder("game"+i)
					.setId("g"+i)
					.setFirst_release_date(10-i)
					.setVote(10-i)
					.build());
		}

		List<User> following = new ArrayList<>();
		for(int i = 0; i < 3; i++){
			User user = new User();
			user.setId("u"+i);
			user.setUsername("user"+i);
			following.add(user);
		}
		User requestOwner = new User();
		requestOwner.setId("requestOwnerId");
		requestOwner.setUsername("requestOwner");
		requestOwner.setFollowing(following.stream().map(User::getId).collect(Collectors.toList()));

		DisplayGamesDTO expected = new DisplayGamesDTO();
		expected.setNewGames(games.subList(0,5));
		expected.setMostPopularGames(games.subList(0,5));
		expected.setNewsFromFriends(new ArrayList<>());

		Mockito.when(gameRepository.findAll()).thenReturn(games);
		Mockito.when(userService.getUserById("requestOwnerId")).thenReturn(requestOwner);
		Mockito.when(userService.getAllUsers(following.stream().map(User::getId).collect(Collectors.toList()))).thenReturn(following);

		try {
			DisplayGamesDTO result = gameRESTService.getDisplayGamesPage("requestOwnerId");
			Assertions.assertEquals(expected,result);
		}catch (Exception e){
			Assertions.assertEquals("",e.getMessage());
		}
	}
}
