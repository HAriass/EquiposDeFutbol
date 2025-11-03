package dux.API.equiposDeFutbol.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dux.API.equiposDeFutbol.DTO.CreateEquipoDTO;
import dux.API.equiposDeFutbol.model.Equipo;
import dux.API.equiposDeFutbol.repository.EquipoRepository;
import dux.API.exception.ConflictException;
import dux.API.exception.RecursoNoEncontradoException;

@ExtendWith(MockitoExtension.class)
public class EquipoServiceImplTest {
    
    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    // --- Objetos de Prueba ---
    private final Long EQUIPO_ID = 1L;
    private final Equipo EQUIPO_MOCK = new Equipo(EQUIPO_ID, "River Plate", "Argentina", "Liga Argentina");
    private final CreateEquipoDTO DTO_MOCK = new CreateEquipoDTO("Boca Juniors", "Argentina", "Liga Argentina");


    //TESTS PARA findAll()
    
    @Test
    @DisplayName("findAll - Debería retornar una lista de equipos")
    void findAll_shouldReturnListOfEquipos() {
        // ARRANGE
        List<Equipo> listaMock = Arrays.asList(
            EQUIPO_MOCK,
            new Equipo(2L, "Barcelona", "España", "La Liga")
        );
        when(equipoRepository.findAll()).thenReturn(listaMock);

        // ACT
        List<Equipo> resultado = equipoService.findAll();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("River Plate", resultado.get(0).getNombre());
        verify(equipoRepository, times(1)).findAll();
    }

    //TESTS PARA findById()
    @Test
    @DisplayName("findById - Debería retornar un equipo si existe")
    void findById_shouldReturnEquipo_whenExists() {
        // ARRANGE
        when(equipoRepository.findById(EQUIPO_ID)).thenReturn(Optional.of(EQUIPO_MOCK));

        // ACT
        Equipo resultado = equipoService.findById(EQUIPO_ID);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(EQUIPO_ID, resultado.getId());
        verify(equipoRepository).findById(EQUIPO_ID);
    }

    @Test
    @DisplayName("findById - Debería lanzar RecursoNoEncontradoException si no existe")
    void findById_shouldThrowException_whenNotExists() {
        // ARRANGE
        when(equipoRepository.findById(EQUIPO_ID)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(RecursoNoEncontradoException.class, () -> {
            equipoService.findById(EQUIPO_ID);
        });
    }

    // TESTS PARA save(CreateEquipoDTO)
    @Test
    @DisplayName("save - Debería guardar un nuevo equipo si el nombre no existe")
    void save_shouldSaveNewEquipo_whenNombreNotExists() {
        // ARRANGE
        when(equipoRepository.existsByNombre(DTO_MOCK.getNombre())).thenReturn(false);
        when(equipoRepository.save(any(Equipo.class))).thenAnswer(invocation -> {
            Equipo equipoGuardado = invocation.getArgument(0);
            equipoGuardado.setId(2L);
            return equipoGuardado;
        });

        // ACT
        Equipo resultado = equipoService.save(DTO_MOCK);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Boca Juniors", resultado.getNombre());
        verify(equipoRepository).existsByNombre(DTO_MOCK.getNombre());
        verify(equipoRepository).save(any(Equipo.class));
    }

    @Test
    @DisplayName("save - Debería lanzar ConflictException si el nombre ya existe")
    void save_shouldThrowConflictException_whenNombreAlreadyExists() {
        // ARRANGE
        when(equipoRepository.existsByNombre(DTO_MOCK.getNombre())).thenReturn(true);

        // ACT & ASSERT
        assertThrows(ConflictException.class, () -> {
            equipoService.save(DTO_MOCK);
        });

        // Verificación de que NUNCA se intentó guardar
        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    // TESTS PARA update(Long id, CreateEquipoDTO)
    @Test
    @DisplayName("update - Debería actualizar un equipo existente y devolverlo")
    void update_shouldUpdateExistingEquipo_andReturnIt() {
        // ARRANGE
        when(equipoRepository.findById(EQUIPO_ID)).thenReturn(Optional.of(EQUIPO_MOCK));

        CreateEquipoDTO dtoActualizado = new CreateEquipoDTO("Manchester Utd", "Premier League", "Inglaterra");

        when(equipoRepository.save(any(Equipo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        Equipo resultado = equipoService.update(EQUIPO_ID, dtoActualizado);

        // ASSERT
        assertNotNull(resultado);
        // Verificar que los campos fueron actualizados
        assertEquals(EQUIPO_ID, resultado.getId()); // El ID debe ser el mismo
        assertEquals("Manchester Utd", resultado.getNombre());
        assertEquals("Premier League", resultado.getLiga());
        assertEquals("Inglaterra", resultado.getPais());

        // Verificar interacciones
        verify(equipoRepository, times(1)).findById(EQUIPO_ID);
        verify(equipoRepository, times(1)).save(any(Equipo.class));
    }

    @Test
    @DisplayName("update - Debería lanzar RecursoNoEncontradoException si el equipo a actualizar no existe")
    void update_shouldThrowException_whenEquipoNotExists() {
        // ARRANGE
        // findById, que es llamado por update, simula no encontrar el recurso
        when(equipoRepository.findById(EQUIPO_ID)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(RecursoNoEncontradoException.class, () -> {
            equipoService.update(EQUIPO_ID, DTO_MOCK);
        });

        // Verificar que el método save NUNCA se llama
        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    //TESTS PARA delete(Long id)

    @Test
    @DisplayName("delete - Debería eliminar un equipo existente")
    void delete_shouldDeleteExistingEquipo_whenExists() {
        // ARRANGE
        when(equipoRepository.findById(EQUIPO_ID)).thenReturn(Optional.of(EQUIPO_MOCK));
        
        // ACT
        equipoService.delete(EQUIPO_ID);

        // ASSERT
        verify(equipoRepository, times(1)).findById(EQUIPO_ID);
        verify(equipoRepository, times(1)).delete(EQUIPO_MOCK);
    }

    @Test
    @DisplayName("delete - Debería lanzar RecursoNoEncontradoException si el equipo a eliminar no existe")
    void delete_shouldThrowException_whenEquipoNotExists() {
        // ARRANGE
        when(equipoRepository.findById(EQUIPO_ID)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(RecursoNoEncontradoException.class, () -> {
            equipoService.delete(EQUIPO_ID);
        });

        // Verificar que el método delete NUNCA se llama
        verify(equipoRepository, never()).delete(any(Equipo.class));
    }
}
