package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.Point;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.PointRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PointServiceTest {
    private PointService pointService;
    private Point point;
    private PointDTO pointDTO;
    private PointRepo pointRepo;
    private UserRepo userRepo;
    private ModelMapper modelMapper;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        pointRepo = Mockito.mock(PointRepo.class);
        userRepo = Mockito.mock(UserRepo.class);
        modelMapper = new ModelMapper();
        pointService = new PointService(pointRepo, userRepo, modelMapper);

        user = new User();
        user.setUserID(1);

        userDTO = new UserDTO();
        userDTO.setUserId(1);

        point = new Point();
        point.setPoints(100);
        point.setUser(user);

        pointDTO = new PointDTO();
        pointDTO.setPoints(100);
        pointDTO.setUser(userDTO);
    }

    @Test
    public void testFindAll() {
        List<Point> points = List.of(point);
        when(pointRepo.findAll()).thenReturn(points);

        List<PointDTO> result = pointService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pointRepo, times(1)).findAll();
    }

    @Test
    public void testFindById_PointExists() {
        when(pointRepo.findById(1)).thenReturn(Optional.of(point));

        PointDTO result = pointService.findById(1);

        assertNotNull(result);
        assertEquals(100, result.getPoints());
        verify(pointRepo, times(1)).findById(1);
    }

    @Test
    public void testFindById_PointNotFound() {
        when(pointRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pointService.findById(1));
    }

    @Test
    public void testCreatePoint_UserExists() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(pointRepo.save(any(Point.class))).thenReturn(point);

        PointDTO result = pointService.createPoint(pointDTO);

        assertNotNull(result);
        assertEquals(100, result.getPoints());
        verify(pointRepo, times(1)).save(any(Point.class));
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    public void testCreatePoint_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pointService.createPoint(pointDTO));
        verify(pointRepo, never()).save(any(Point.class));
    }

    @Test
    public void testDeleteById_PointExists() {
        when(pointRepo.findById(1)).thenReturn(Optional.of(point));

        boolean result = pointService.deleteById(1);

        assertTrue(result);
        verify(pointRepo, times(1)).findById(1);
        verify(pointRepo, times(1)).deleteById(point.getUser().getUserID());
    }

    @Test
    public void testDeleteById_PointNotFound() {
        when(pointRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pointService.deleteById(1));
        verify(pointRepo, never()).deleteById(anyInt());
    }

    @Test
    public void testUpdatePoint() {
        when(pointRepo.findById(1)).thenReturn(Optional.of(point));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(pointRepo.save(any(Point.class))).thenReturn(point);

        PointDTO result = pointService.updatePoint(1, pointDTO);

        assertNotNull(result);
        assertEquals(100, result.getPoints());
        verify(pointRepo, times(1)).findById(1);
        verify(pointRepo, times(1)).save(any(Point.class));
    }
}
