package com.solactive;

import com.solactive.dao.StatisticsDAO;
import com.solactive.dao.TickDAO;
import com.solactive.dataStore.TicksDataStore;
import com.solactive.exception.DataAccessException;
import com.solactive.exception.TickProcessingException;
import com.solactive.repository.StatisticsRepository;
import com.solactive.repository.impl.StatisticsRepositoryImpl;
import com.solactive.repository.impl.TickRepositoryImpl;
import com.solactive.service.DefaultStatisticsService;
import com.solactive.service.DefaultTickService;
import com.solactive.util.TickStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SolactiveCodingTestApplicationTests {

	private TickDAO tickDAO;

	@InjectMocks
	DefaultTickService tickService;

	@InjectMocks
	DefaultStatisticsService statisticsService;

	@InjectMocks
	StatisticsRepositoryImpl statisticsRepository;

	@InjectMocks
	TickRepositoryImpl tickRepository;

	@InjectMocks
	TicksDataStore ticksDataStore;

	@Mock
	StatisticsRepository statisticsRepositoryMock;

	@Mock
	TickRepositoryImpl tickRepositoryMock;

	@Mock
	TicksDataStore tickDataStoreMock;

	@Before
	public void setup(){
		tickDAO = new TickDAO("IBM.N",143.2,System.currentTimeMillis());
		ticksDataStore.setTicksDataPerSecond(new LinkedHashMap<>(60));
		ticksDataStore.setTicksDataPerInstrumentPerSecond(new HashMap<>());
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testTickPosting(){
		Mockito.doNothing().when(tickRepositoryMock).addTick(tickDAO);
		TickStatus tickStatus = null;
		try {
			tickStatus = tickService.addTick(tickDAO);
		} catch (TickProcessingException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(tickStatus.name(),TickStatus.CREATED.name());
	}

	@Test
	public void testTickPostingOn60Seconds(){
		TickDAO td = new TickDAO("IBM.N",143.2,System.currentTimeMillis()-60000);
		Mockito.doNothing().when(tickRepositoryMock).addTick(td);
		TickStatus tickStatus = null;
		try {
			tickStatus = tickService.addTick(td);
		} catch (TickProcessingException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(tickStatus.name(),TickStatus.CREATED.name());
	}

	@Test
	public void testTickPostingMoreThan60Seconds(){
		TickDAO td = new TickDAO("IBM.N",143.2,System.currentTimeMillis()-61000);
		Mockito.doNothing().when(tickRepositoryMock).addTick(td);
		TickStatus tickStatus = null;
		try {
			tickStatus = tickService.addTick(td);
		} catch (TickProcessingException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(tickStatus.name(),TickStatus.NOT_ACCEPTED.name());
	}


	@Test
	public void testStatisticsForTicksInLast60Seconds() throws DataAccessException {
		Mockito.when(statisticsRepositoryMock.get(60,null)).thenReturn(new StatisticsDAO(182.0,143.2,38.8,2));

		StatisticsDAO statisticsDao = statisticsService.getStatistics();
		Assert.assertArrayEquals("Statistics Not matching",new double[]{182.0,143.2,38.8},new double[]{statisticsDao.getSum(),statisticsDao.getMax(),statisticsDao.getMin()},0.0);
		Assert.assertEquals(statisticsDao.getCount(),2);
	}

	@Test
	public void testStatisticsForTicksOver60Seconds() throws DataAccessException {
		Mockito.when(statisticsRepositoryMock.get(60,null)).thenReturn(new StatisticsDAO());

		StatisticsDAO statisticsDao = statisticsService.getStatistics();
		Assert.assertArrayEquals("Statistics Not matching",new double[]{0.0,0.0},new double[]{statisticsDao.getSum(),statisticsDao.getMax()},0.0);
		Assert.assertEquals(statisticsDao.getCount(),0);
	}


	@Test
	public void testStatisticsRepoForTicksInLast60Seconds() throws DataAccessException {
		long timestampInSeconds = System.currentTimeMillis()/1000;
		Mockito.when(tickDataStoreMock.getStatisticsPerInstrumentByTime(null,timestampInSeconds)).thenReturn(new StatisticsDAO(182.0,143.2,38.8,2));
		StatisticsDAO statisticsDao = statisticsRepository.get(60,null);
		Assert.assertArrayEquals("Statistics Not matching",new double[]{182.0,143.2,38.8},new double[]{statisticsDao.getSum(),statisticsDao.getMax(),statisticsDao.getMin()},0.0);
		Assert.assertEquals(statisticsDao.getCount(),2);
	}

	@Test
	public void testStatisticsRepoForTicksOver60Seconds() throws DataAccessException {
		long timestampInSeconds = (System.currentTimeMillis()/1000) + 1;
		Mockito.when(tickDataStoreMock.getStatisticsPerInstrumentByTime(null,timestampInSeconds)).thenReturn(new StatisticsDAO(182.0,143.2,38.8,2));
		StatisticsDAO statisticsDao = statisticsRepository.get(60,null);
		Assert.assertArrayEquals("Statistics Not matching",new double[]{0.0,0.0},new double[]{statisticsDao.getSum(),statisticsDao.getMax()},0.0);
		Assert.assertEquals(statisticsDao.getCount(),0);
	}

	@Test
	public void testStatisticsRepoOfInstrumentForTicksInLast60Seconds() throws DataAccessException {
		long timestampInSeconds = System.currentTimeMillis()/1000;
		Mockito.when(tickDataStoreMock.getStatisticsPerInstrumentByTime("IBM.N",timestampInSeconds)).thenReturn(new StatisticsDAO(182.0,143.2,38.8,2));
		StatisticsDAO statisticsDao = statisticsRepository.get(60,"IBM.N");
		Assert.assertArrayEquals("Statistics Not matching",new double[]{182.0,143.2,38.8},new double[]{statisticsDao.getSum(),statisticsDao.getMax(),statisticsDao.getMin()},0.0);
		Assert.assertEquals(statisticsDao.getCount(),2);
	}

	@Test
	public void testStatisticsRepoOfInstrumentForTicksOver60Seconds() throws DataAccessException {
		long timestampInSeconds = (System.currentTimeMillis()/1000) + 1;
		Mockito.when(tickDataStoreMock.getStatisticsPerInstrumentByTime("IBM.N",timestampInSeconds)).thenReturn(new StatisticsDAO(182.0,143.2,38.8,2));
		StatisticsDAO statisticsDao = statisticsRepository.get(60,"IBM.N");
		Assert.assertArrayEquals("Statistics Not matching",new double[]{0.0,0.0},new double[]{statisticsDao.getSum(),statisticsDao.getMax()},0.0);
		Assert.assertEquals(statisticsDao.getCount(),0);
	}

	@Test
	public void testStatisticsInTickRepo(){
		long timestampInMilliSeconds = System.currentTimeMillis();
		TickDAO tick = new TickDAO("IBM.N",43.2,timestampInMilliSeconds);
		StatisticsDAO statisticsDAO = new StatisticsDAO(143.2,143.2,143.2,1);
		StatisticsDAO statisticsDAOPerInstrument = new StatisticsDAO(143.2,143.2,143.2,1);
		Mockito.when(tickDataStoreMock.getStatisticsByTime(timestampInMilliSeconds/1000)).thenReturn(statisticsDAO);
		Mockito.when(tickDataStoreMock.getStatisticsPerInstrumentByTime("IBM.N",timestampInMilliSeconds/1000)).thenReturn(statisticsDAOPerInstrument);
		tickRepository.addTick(tick);
		Assert.assertArrayEquals(new double[]{186.4,143.2,43.2,2},new double[]{statisticsDAO.getSum(),statisticsDAO.getMax(),statisticsDAO.getMin(),statisticsDAO.getCount()},0.1);
		Assert.assertArrayEquals(new double[]{186.4,143.2,43.2,2},new double[]{statisticsDAOPerInstrument.getSum(),statisticsDAOPerInstrument.getMax(),statisticsDAOPerInstrument.getMin(),statisticsDAOPerInstrument.getCount()},0.1);

	}


	@Test
	public void testAddStatisticsForTickInDataStore(){
		long timestampInMilliSeconds = System.currentTimeMillis();
		TickDAO td = new TickDAO("IBM.N",143.2,timestampInMilliSeconds);
		StatisticsDAO sd = new StatisticsDAO(143.2,143.2,143.2,1);
		ticksDataStore.addStatisticsForTick(td,sd);
		//tickRepository.addTick(td);
		Assert.assertEquals(sd,ticksDataStore.getStatisticsByTime(timestampInMilliSeconds/1000));
	}

	@Test
	public void testAddStatisticsPerInstrumentForTickInDataStore(){
		long timestampInMilliSeconds = System.currentTimeMillis();
		TickDAO td = new TickDAO("IBM.N",143.2,timestampInMilliSeconds);
		StatisticsDAO sd = new StatisticsDAO(143.2,143.2,143.2,1);
		ticksDataStore.addStatisticsForTickPerInstrument(td,sd);
		//tickRepository.addTick(td);
		Assert.assertEquals(sd,ticksDataStore.getStatisticsPerInstrumentByTime("IBM.N",timestampInMilliSeconds/1000));
	}


}
