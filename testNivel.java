package fraizPinto;
//JUnit 5
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import avion.Avion;
import avion.AvionComputarizado;
import avion.AvionPesado;
//
import avion.AvionSimple;
import java.util.ArrayList;
import java.util.List;
import copControl.Dificultad;
import copControl.Juego;
import copControl.Jugador;
import copControl.Mapa;
import copControl.Nivel;
import copControl.Posicion;
import pista.Pista;
import pista.PistaSimple;
import pista.PosicionesEntradaVaciaException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class testNivel {

		Mapa mapa;
		
		Dificultad dificultad;
		
		Nivel nivel1, nivel2, nivel3;
		
		Posicion posicionInicial1, posicionInicial2, posicionInicial3, posicionInicial4,  posicionFinal1, posicionFinal2, posicionFinal3, posicionLimite;

		PistaSimple pistaSimple1, pistaSimple2, pistaSimple3;
		
		AvionSimple avionSimple1, avionSimple2;
		
		AvionPesado avionPesado1;
		
		Jugador jugador1, jugador2;
		
		Juego juego, juego2;
		
		
		@BeforeEach
		public void setUp() throws Exception{
			

			
			Posicion pos1 = new Posicion(5,10);			
			Pista pistaSimple1 = new PistaSimple(pos1);
			
			List<Pista> pistas1 = new ArrayList<>();
			pistas1.add(pistaSimple1);
			
			mapa = new Mapa(pistas1);			
			dificultad = new Dificultad(10, 15, 5);			
			nivel1 = new Nivel(mapa, dificultad);
			
			List<Nivel> niveles = new ArrayList<>();			
			niveles.add(nivel1);
			niveles.add(nivel2);
			niveles.add(nivel3);
			 
			posicionInicial1 = new Posicion (1,1);
			posicionFinal1 = new Posicion (5, 5);
			posicionInicial2 = new Posicion (10, 10);
			posicionFinal2 = new Posicion (15, 15);
			posicionInicial3 = new Posicion (50, 50);
			posicionFinal3 = new Posicion(25, 25);			
			posicionLimite = new Posicion(501, 501);
			
			avionSimple1 = new AvionSimple(posicionInicial1, posicionFinal1, mapa);
			avionSimple2 = new AvionSimple(posicionInicial2, posicionFinal2, mapa);
			avionPesado1 = new AvionPesado(posicionInicial3, posicionFinal3, mapa);
			
			jugador1 = new Jugador("Fraiz");
			juego = new Juego(jugador1, niveles);
			jugador2 = new Jugador("Pinto");
			juego2 = new Juego(jugador2, niveles);
			
		}
		
	
		@Test
		public void testAgregarAvionPesado() {	
			nivel1.colocarAvionEnAire(avionPesado1);
			List<Avion>aviones = juego.getNivelActual().getAvionesVolando();
			
			assertEquals(aviones.get(0), avionPesado1);
					
		}

		@Test
		public void testAvionPesadoPuedeAterrizar(){
		assertTrue(nivel1.tienePistaAdecuada(avionPesado1), "El avion no tiene pista para su tipo");	
						
		}
		
		@Test
		public void testIrAlSiguienteNivel() {
			juego.vivir();
			Nivel n = juego.getNivelActual();
			juego.avanzarNivel();
			assertNotEquals(juego.getNivelActual(), n);
		} 
		
		@Test
		public void TestAvanzarAvionesEnAire() {
			Pista pista = juego.getNivelActual().getMapa().getPistas().get(0);
			Avion avion = new AvionSimple(new Posicion(30, 30), pista.getPosicionEntrada(), juego.getNivelActual().getMapa());
			Posicion primerPosicion; 
			
			juego.getNivelActual().colocarAvionEnAire(avion);
			primerPosicion = juego.getNivelActual().getAvionesVolando().get(0).getPosicionActual();
			juego.getNivelActual().avanzarAvionesEnAire();
			assertNotEquals(primerPosicion, juego.getNivelActual().getAvionesVolando().get(0).getPosicionActual());
			
		}
		
		@Test
		public void TestAterrizarAvionSimple() {
			Pista pista = juego.getNivelActual().getMapa().getPistas().get(0);
			Avion avion = new AvionSimple(pista.getPosicionEntrada(), new Posicion(40, 40), juego.getNivelActual().getMapa());

			juego.getNivelActual().colocarAvionEnAire(avion);
			juego.getNivelActual().getAvionesVolando().get(0).aterrizar(pista);
			juego.vivir();

			int avionesAterrizados = juego.getCantidadAvionesAterrizados();
			assertEquals(avionesAterrizados, 1);
		}
		
		@Test
		public void testObtenerAvionEnIgualPosicion() {
			nivel1.colocarAvionEnAire(avionSimple1);
			Avion a = nivel1.getAvionEnPosicion(avionSimple1.getPosicionActual());
			
			assertTrue(avionSimple1.equals(a), "Las posiciones son iguales");
		}
		
		@Test
		public void testAvionChocaConOtroAvion() {
			nivel1.colocarAvionEnAire(avionSimple1);
			nivel1.colocarAvionEnAire(avionSimple2);
			assertTrue("No se detecto el choque entre dos aviones", nivel1.huboChoque());
		}

		
	}


