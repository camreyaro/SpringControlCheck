
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Tremite;

@Repository
public interface TremiteRepository extends JpaRepository<Tremite, Integer> {
	
	@Query("select t from Tremite t where t.administrator.id = ?1")
	Collection<Tremite> tremitesByAdministratorId(Integer administratorId);
	
	@Query("select t from Tremite t where t.newspaper.id = ?1 and t.moment<=CURRENT_TIMESTAMP")
	Collection<Tremite> tremitesByNewspaperId(Integer newspaperId);
	
	@Query("select t from Tremite t where t.newspaper.id = ?1")
	Collection<Tremite> allTremitesByNewspaperId(Integer newspaperId);
	
	//The ratio of tremites per newspaper
	@Query("select 1.0*((select count(n) from Newspaper n where (select count(x) from Tremite x where x.newspaper.id=n.id)>0/count(p))) from Newspaper p")
	Double getTremitesRatioPerNewspaper();
	
	//The admin with more tremites
//	@Query("select a from Administrator a where (select count(x) from Tremite x where x.administrator.id=a.id)>= (select max(1.0*(select count(x) from Tremite x where x.administrator.id=a.id)) from Administrator a)")
//	Administrator getMaxTremitesAdmin();
}
