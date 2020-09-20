package com.mychum1.explorer;

import com.mychum1.explorer.controller.ApiController;
import com.mychum1.explorer.domain.HotPlace;
import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.exception.SearchException;
import com.mychum1.explorer.repository.HotPlaceRepository;
import com.mychum1.explorer.service.HotPlaceService;
import com.mychum1.explorer.service.kakao.KaKaoSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ExplorerApplication.class)
@AutoConfigureMockMvc
class ExplorerApplicationTests {

	@Autowired
	private HotPlaceService hotPlaceService;

	@Autowired
	private KaKaoSearchService kaKaoSearchService;
//
	private MockMvc mockMvc;

	@Autowired
	private HotPlaceRepository hotPlaceRepository;

	@Autowired
	private ApiController apiController;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(apiController)
				.addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
				.build();
	}

	/**
	 * 장소 검색 api 테스트
	 * @throws Exception
	 */
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testKaKaoPlaces() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/kakao/places?keyword=카카오프렌즈&size=15&page=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

	}

	/**
	 * 인기키워드 탑 랭킹 api 테스트
	 * @throws Exception
	 */
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testRank() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/rank?num=10"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

	}

	/**
	 * 인기 키워드 저장 테스트
	 */
	@Test
	public void testSaveHotPlace() {
		HotPlace result = hotPlaceRepository.save(new HotPlace("카카오프렌즈",1));
		assertThat(result.getKeyword()).isEqualTo("카카오프렌즈");
		assertThat(result.getCount()).isEqualTo(1);
	}

	/**
	 * 인기 키워드 카운트 업데이트 테스트
	 */
	@Test
	public void testUpdateHotPlace() {
		Integer result = hotPlaceService.updateHotKeyword("카카오프렌즈");
		assertThat(result).isEqualTo(1);
	}

	/**
	 * 인기 키워드 탑 10 테스트
	 */
	@Test
	public void testTopRanking() {
		List<HotPlace> result = hotPlaceService.topRanking(10);
		assertThat(result.size()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(10);
		testUpdateHotPlace();
		hotPlaceService.topRanking(10);
		assertThat(result.size()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(10);
	}

	/**
	 * 장소검색 메소드 테스트
	 */
	@Test
	public void searchPlacesByKeyword() throws SearchException {
		KaKaoDocuments kaKaoDocuments = (KaKaoDocuments) kaKaoSearchService.searchPlacesByKeyword("카카오프렌즈", 1, 10);
		assertThat(kaKaoDocuments).isNotNull();
	}

	/**
	 * 인기키워드 저장 후 업데이트 테스트
	 */
	@Test
	public void saveHotPlaceAndUpdate() throws SearchException {
		testSaveHotPlace();
		hotPlaceService.updateHotKeyword("카카오프렌즈");

		assertThat(hotPlaceRepository.findById("카카오프렌즈").get().getCount()).isEqualTo(2);
	}


}
