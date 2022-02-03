package monprojet.dao;
import java.util.*;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void CompteEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int nombreDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals( nombreDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

    @Test
    void NbreHabitantsTest(){
        int populationTotal = 12+20;
        //int popFonction = countryDAO.populationParPays(1);
        assertEquals(populationTotal, countryDAO.NbreHabitants(1),"Le resultat doit être 32");
    }

    @Test
    void ListeDeLaPopulationParPaysTest(){
        List<Tuple> Liste = countryDAO.ListeDeLaPopulationParPays();
        assertEquals(12+15, Liste.get(0).get(1));
      
        assertEquals(18, Liste.get(1).get(1));
       
        assertEquals(27, Liste.get(2).get(1));
        
    }

}