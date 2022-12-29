package com.gameinn.user.service;

import com.gameinn.user.service.dataTypes.GameLog;
import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.feignClient.GameService;
import com.gameinn.user.service.model.Game;
import com.gameinn.user.service.repository.UserRepository;
import com.gameinn.user.service.service.UserRESTService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceApplicationTests {

	@InjectMocks
	private UserRESTService userRESTService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private GameService gameService;
	@Before
	public void setUp(){
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void getAllUsersTest() {
		List<User> users = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			User user = new User();
			user.setId("user"+i);
			user.setEmail("user"+i+"@gameinn.com");
			user.setPassword("user"+i+"password");
			user.setLogs(new ArrayList<>());
			user.setFollowers(new ArrayList<>());
			user.setFavoriteGames(new ArrayList<>());
			user.setBio("bio");
			user.setFollowing(new ArrayList<>());
			user.setUsername("username"+i);
			user.setProfileImage("image");
			user.setToPlayList(new ArrayList<>());
			users.add(user);
		}
		Mockito.when(userRepository.findAll()).thenReturn(users);

		List<UserReadDTO> result = userRESTService.getAllUsers();

		Assertions.assertEquals(5,result.size());
	}

	@Test
	void addUserTest(){
		User user = new User();
		user.setId("user");
		user.setEmail("user@gameinn.com");
		user.setPassword("userpassword");
		user.setLogs(new ArrayList<>());
		user.setFollowers(new ArrayList<>());
		user.setFavoriteGames(new ArrayList<>());
		user.setBio("bio");
		user.setFollowing(new ArrayList<>());
		user.setUsername("username");
		user.setProfileImage("image");
		user.setToPlayList(new ArrayList<>());

		Mockito.when(userRepository.insert(any(User.class))).thenReturn(user);

		UserReadDTO result = userRESTService.addUser(new UserCreateUpdateDTO());

		Assertions.assertNotNull(result);
	}

	@Test
	void getUserByIdTest(){
		User user = new User();
		user.setId("user");
		user.setEmail("user@gameinn.com");
		user.setPassword("userpassword");
		user.setLogs(new ArrayList<>());
		user.setFollowers(new ArrayList<>());
		user.setFavoriteGames(new ArrayList<>());
		user.setBio("bio");
		user.setFollowing(new ArrayList<>());
		user.setUsername("username");
		user.setProfileImage("image");

		Mockito.when(userRepository.findUserById("user")).thenReturn(Optional.of(user));

		UserReadDTO result = userRESTService.getUserById("user");

		Assertions.assertNotNull(result);
	}

	@Test
	void getUserByInvalidIdTest(){
		User user = new User();
		user.setId("user");
		user.setEmail("user@gameinn.com");
		user.setPassword("userpassword");
		user.setLogs(new ArrayList<>());
		user.setFollowers(new ArrayList<>());
		user.setFavoriteGames(new ArrayList<>());
		user.setBio("bio");
		user.setFollowing(new ArrayList<>());
		user.setUsername("username");
		user.setProfileImage("image");

		Mockito.when(userRepository.findUserById("user")).thenReturn(Optional.empty());

		try{
			UserReadDTO result = userRESTService.getUserById("user");
			Assertions.assertNotNull(result);
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given userId",e.getMessage());
		}
	}

	@Test
	void getUsersByIdTest(){
		List<User> users = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			User user = new User();
			user.setId("user"+i);
			user.setEmail("user"+i+"@gameinn.com");
			user.setPassword("user"+i+"password");
			user.setLogs(new ArrayList<>());
			user.setFollowers(new ArrayList<>());
			user.setFavoriteGames(new ArrayList<>());
			user.setBio("bio");
			user.setFollowing(new ArrayList<>());
			user.setUsername("username"+i);
			user.setProfileImage("image");
			user.setToPlayList(new ArrayList<>());
			users.add(user);
		}

		Mockito.when(userRepository.findAllById(Arrays.asList("user0","user1","user2","user3","user4"))).thenReturn(users);

		List<UserReadDTO> result = userRESTService.getUsersById(Arrays.asList("user0","user1","user2","user3","user4"));

		Assertions.assertEquals(5,result.size());
	}

	@Test
	void getUserByEmailAndPassword(){
		User user = new User();
		user.setId("user");
		user.setEmail("user@gameinn.com");
		user.setPassword("userpassword");
		user.setLogs(new ArrayList<>());
		user.setFollowers(new ArrayList<>());
		user.setFavoriteGames(new ArrayList<>());
		user.setBio("bio");
		user.setFollowing(new ArrayList<>());
		user.setUsername("username");
		user.setProfileImage("image");

		Mockito.when(userRepository.findUserByEmailAndPassword("user@gameinn.com","userpassword")).thenReturn(Optional.of(user));

		UserCreateUpdateDTO request = new UserCreateUpdateDTO();
		request.setPassword("userpassword");
		request.setEmail("user@gameinn.com");
		UserReadDTO result = userRESTService.getUserByEmailAndPassword(request);

		Assertions.assertNotNull(result);
	}

	@Test
	void getUserByInvalidEmailAndPassword(){

		Mockito.when(userRepository.findUserByEmailAndPassword("user@gameinn.com","userpassword")).thenReturn(Optional.empty());

		UserCreateUpdateDTO request = new UserCreateUpdateDTO();
		request.setPassword("userpassword");
		request.setEmail("user@gameinn.com");
		try{
			UserReadDTO result = userRESTService.getUserByEmailAndPassword(request);
			Assertions.assertNotNull(result);
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given email-password",e.getMessage());
		}
	}

	@Test
	void updateUserTest(){
		List<String> favoriteGames = new ArrayList<>(Arrays.asList("g1","g2"));
		List<String> toPlayList = new ArrayList<>(Arrays.asList("gt1","gt2"));

		User oldUser = new User.UserBuilder("username","password","user@gameinn.com")
				.setId("userId")
				.setBio("oldbio")
				.setFavoriteGames(new ArrayList<>())
				.setFollowers(new ArrayList<>())
				.setFollowing(new ArrayList<>())
				.setProfileImage("image")
				.setLogs(new ArrayList<>())
				.setToPlayList(new ArrayList<>())
				.build();
		User newUser = new User.UserBuilder("username","password","updated@gameinn.com")
				.setId("userId")
				.setBio("updatedBio")
				.setFavoriteGames(favoriteGames)
				.setFollowers(new ArrayList<>())
				.setFollowing(new ArrayList<>())
				.setProfileImage("image")
				.setLogs(new ArrayList<>())
				.setToPlayList(toPlayList)
				.build();

		UserCreateUpdateDTO updatedUser = new UserCreateUpdateDTO();
		updatedUser.setEmail("updated@gameinn.com");
		updatedUser.setUsername("username");
		updatedUser.setPassword("password");
		updatedUser.setFavoriteGames(favoriteGames);
		updatedUser.setLogs(new ArrayList<>());
		updatedUser.setBio("updatedBio");
		updatedUser.setProfileImage("image");
		updatedUser.setToPlayList(toPlayList);

		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.ofNullable(oldUser));
		Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

		UserReadDTO result = userRESTService.updateUser("userId",updatedUser);

		Assertions.assertEquals(result.getId(),newUser.getId());
		Assertions.assertEquals(result.getUsername(),newUser.getUsername());
		Assertions.assertEquals(result.getEmail(),newUser.getEmail());
		Assertions.assertEquals(result.getBio(),newUser.getBio());
		Assertions.assertEquals(result.getFollowing(),newUser.getFollowing());
		Assertions.assertEquals(result.getFollowers(),newUser.getFollowers());
		Assertions.assertEquals(result.getToPlayList(),newUser.getToPlayList());
		Assertions.assertEquals(result.getFavoriteGames(),newUser.getFavoriteGames());
		Assertions.assertEquals(result.getLogs(),newUser.getLogs());
		Assertions.assertEquals(result.getProfileImage(),newUser.getProfileImage());
	}

	@Test
	void updateInvalidUser(){
		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.empty());
		try{
			userRESTService.updateUser("userId",new UserCreateUpdateDTO());
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given userId",e.getMessage());
		}
	}

	@Test
	void getToPlayList(){
		User user = new User.UserBuilder("username","password","email@gameinn.com")
				.setToPlayList(Arrays.asList("g1","g2"))
				.setId("userId")
				.build();

		List<Game> games = new ArrayList<>();
		for(int i = 1; i < 3; i++){
			Game game = new Game();
			game.setId("g" + i);
			game.setName("game" + i);
			games.add(game);
		}

		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.of(user));
		Mockito.when(gameService.getAllGames(Arrays.asList("g1","g2"))).thenReturn(games);

		List<Game> result = userRESTService.getToPlayList("userId");

		Assertions.assertEquals(games.size(),result.size());
		Assertions.assertEquals(games,result);
	}

	@Test
	void getToPlayListInvalid(){
		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.empty());
		try{
			userRESTService.getToPlayList("userId");
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given userId",e.getMessage());
		}
	}

	@Test
	void followUserTest(){
		User src = new User.UserBuilder("src","password","src@gameinn.com")
				.setId("srcId")
				.build();

		User dest = new User.UserBuilder("dest","password","src@gameinn.com")
				.setId("destId")
				.build();

		Mockito.when(userRepository.findUserById("srcId")).thenReturn(Optional.of(src));
		Mockito.when(userRepository.findUserById("destId")).thenReturn(Optional.of(dest));

		UserReadDTO result = userRESTService.followUser("srcId","destId");

		Assertions.assertTrue(result.getFollowing().contains("destId"));
	}

	@Test
	void followUserInvalidSrcTest(){
		User dest = new User.UserBuilder("dest","password","src@gameinn.com")
				.setId("destId")
				.build();

		Mockito.when(userRepository.findUserById("srcId")).thenReturn(Optional.empty());
		Mockito.when(userRepository.findUserById("destId")).thenReturn(Optional.of(dest));

		try{
			UserReadDTO result = userRESTService.followUser("srcId","destId");
			Assertions.assertTrue(result.getFollowing().contains("destId"));
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: srcId",e.getMessage());
		}
	}

	@Test
	void followUserInvalidDestTest(){
		User src = new User.UserBuilder("src","password","src@gameinn.com")
				.setId("srcId")
				.build();

		Mockito.when(userRepository.findUserById("srcId")).thenReturn(Optional.of(src));
		Mockito.when(userRepository.findUserById("destId")).thenReturn(Optional.empty());

		try{
			UserReadDTO result = userRESTService.followUser("srcId","destId");
			Assertions.assertTrue(result.getFollowing().contains("destId"));
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: destId",e.getMessage());
		}
	}

	@Test
	void unFollowUserTest(){
		List<String> following = new ArrayList<>();
		List<String> followers = new ArrayList<>();
		followers.add("srcId");
		following.add("destId");
		User src = new User.UserBuilder("src","password","src@gameinn.com")
				.setId("srcId")
				.setFollowing(following)
				.build();

		User dest = new User.UserBuilder("dest","password","src@gameinn.com")
				.setId("destId")
				.setFollowers(followers)
				.build();

		Mockito.when(userRepository.findUserById("srcId")).thenReturn(Optional.of(src));
		Mockito.when(userRepository.findUserById("destId")).thenReturn(Optional.of(dest));

		UserReadDTO result = userRESTService.unfollowUser("srcId","destId");

		Assertions.assertFalse(result.getFollowing().contains("destId"));
	}

	@Test
	void unFollowUserInvalidSrcTest(){
		List<String> followers = new ArrayList<>();
		followers.add("srcId");

		User dest = new User.UserBuilder("dest","password","src@gameinn.com")
				.setId("destId")
				.setFollowers(followers)
				.build();

		Mockito.when(userRepository.findUserById("srcId")).thenReturn(Optional.empty());
		Mockito.when(userRepository.findUserById("destId")).thenReturn(Optional.of(dest));

		try{
			UserReadDTO result = userRESTService.unfollowUser("srcId","destId");
			Assertions.assertTrue(result.getFollowing().contains("destId"));
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: srcId",e.getMessage());
		}
	}

	@Test
	void unFollowUserInvalidDestTest(){
		List<String> following = new ArrayList<>();
		following.add("destId");
		User src = new User.UserBuilder("src","password","src@gameinn.com")
				.setId("srcId")
				.setFollowing(following)
				.build();

		Mockito.when(userRepository.findUserById("srcId")).thenReturn(Optional.of(src));
		Mockito.when(userRepository.findUserById("destId")).thenReturn(Optional.empty());

		try{
			UserReadDTO result = userRESTService.unfollowUser("srcId","destId");
			Assertions.assertTrue(result.getFollowing().contains("destId"));
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: destId",e.getMessage());
		}
	}

	@Test
	void addGameLog(){
		User user = new User.UserBuilder("username","password","email@gameinn.com")
				.setId("userId")
				.build();
		GameLog gameLog = new GameLog();
		gameLog.setGameId("gameId");
		List<GameLog> expectedLogs = new ArrayList<>();
		expectedLogs.add(gameLog);

		User expected = new User.UserBuilder("username","password","email@gameinn.com")
				.setId("userId")
				.setLogs(expectedLogs)
				.build();
		UserReadDTO exp = new UserReadDTO();
		exp.setUsername("username");
		exp.setEmail("email@gameinn.com");
		exp.setId("userId");
		exp.setLogs(expectedLogs);

		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.ofNullable(user));
		Mockito.when(userRepository.save(expected)).thenReturn(expected);

		UserReadDTO result = userRESTService.addGameLog("userId",gameLog);
		Assertions.assertEquals(exp,result);
	}

	@Test
	void addGameLogInvalidUserId(){
		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.empty());
		try{
			userRESTService.addGameLog("userId",new GameLog());
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: userId",e.getMessage());
		}
	}

	@Test
	void getFollowingTest(){
		List<UserReadDTO> followings = new ArrayList<>();
		List<User> dbResult = new ArrayList<>();
		User user = new User.UserBuilder("username","password","user@gameinn.com")
				.setId("MockId")
				.setFollowing(new ArrayList<>())
				.build();
		for(int i = 0; i < 10; i++){
			UserReadDTO userReadDTO = new UserReadDTO();
			userReadDTO.setId("user"+i);
			userReadDTO.setUsername("username"+i);
			userReadDTO.setEmail("user"+i+"@gameinn.com");
			followings.add(userReadDTO);
			user.getFollowing().add("user"+i);

			User user1 = new User.UserBuilder("username"+i,"password","user"+i+"@gameinn.com")
					.setId("user"+i)
					.build();
			dbResult.add(user1);
		}

		Mockito.when(userRepository.findUserById("MockId")).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findAllById(user.getFollowing())).thenReturn(dbResult);

		List<UserReadDTO> result = userRESTService.getFollowing("MockId");
		Assertions.assertEquals(followings,result);
	}

	@Test
	void getFollowerTest(){
		List<UserReadDTO> followers = new ArrayList<>();
		List<User> dbResult = new ArrayList<>();
		User user = new User.UserBuilder("username","password","user@gameinn.com")
				.setId("MockId")
				.setFollowers(new ArrayList<>())
				.build();
		for(int i = 0; i < 10; i++){
			UserReadDTO userReadDTO = new UserReadDTO();
			userReadDTO.setId("user"+i);
			userReadDTO.setUsername("username"+i);
			userReadDTO.setEmail("user"+i+"@gameinn.com");
			followers.add(userReadDTO);
			user.getFollowers().add("user"+i);

			User user1 = new User.UserBuilder("username"+i,"password","user"+i+"@gameinn.com")
					.setId("user"+i)
					.build();
			dbResult.add(user1);
		}

		Mockito.when(userRepository.findUserById("MockId")).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findAllById(user.getFollowers())).thenReturn(dbResult);

		List<UserReadDTO> result = userRESTService.getFollowers("MockId");
		Assertions.assertEquals(followers,result);
	}

	@Test
	void getFollowingInvalidUserIdTest(){
		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.empty());
		try{
			userRESTService.getFollowing("userId");
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: userId",e.getMessage());
		}
	}

	@Test
	void getFollowersInvalidUserIdTest(){
		Mockito.when(userRepository.findUserById("userId")).thenReturn(Optional.empty());
		try{
			userRESTService.getFollowers("userId");
		}catch (Exception e){
			Assertions.assertEquals("There is no user matches with given id: userId",e.getMessage());
		}
	}
}
