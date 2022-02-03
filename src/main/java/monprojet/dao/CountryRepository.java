package monprojet.dao;

import java.util.List;
import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import monprojet.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {
 
    @Query (value="SELECT SUM(POPULATION) "
    + "FROM COUNTRY INNER JOIN CITY c ON c.COUNTRY = COUNTRY.id "
    + "WHERE COUNTRY.id = :id",
    nativeQuery = true)
public int NbreHabitants(@Param("id")int id);

@Query (value="SELECT COUNTRY2.name, (SELECT SUM(CITY.population)"
    + "FROM COUNTRY COUNTRY1 INNER JOIN CITY ON CITY.country = COUNTRY1.id"
    + "WHERE COUNTRY1.id = COUNTRY2.id)"
    + "FROM COUNTRY COUNTRY2"
    + "ORDER BY COUNTRY2.name",
    nativeQuery = true)
public List<Tuple>ListeDeLaPopulationParPays();
    
}

