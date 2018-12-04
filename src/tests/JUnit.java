package tests;

import org.junit.Before;
import junit.framework.TestCase;

public class JUnit extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	public void testUsuarioRegistrado(){
		int resul = contenedores.GestionFicheros.usuarioRegistrado("paco", "paco");
		assertEquals(2, resul);
	}

}
