
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;
import domain.Volumen;

@Repository
public interface VolumenRepository extends JpaRepository<Volumen, Integer> {

	@Query("select n from Volumen v join v.newspapers n where n.publicNp=1 and v.id=?1")
	Collection<Newspaper> getPublicNewspaper(Integer id);

	@Query("select n from Volumen v join v.newspapers n where n.publicNp=0 and v.id=?1")
	Collection<Newspaper> getPrivateNewspaper(Integer id);

	@Query("select n from Volumen v join v.newspapers n where v.id=?1")
	Collection<Newspaper> getAllNewspaper(Integer id);

	@Query("select v from Volumen v where (select p from Newspaper p where p.id=?1) member of v.newspapers")
	Collection<Volumen> getVolumensOfNewspaper(Integer id);

	@Query("select sv.volumen from SuscriptionVolumen sv where sv.customer.id=?1")
	Collection<Volumen> getVolumensByCustomer(Integer customerId);

	@Query("select v from Volumen v where v.user.id=?1")
	Collection<Volumen> getVolumensByUser(Integer userId);

	@Query("select v from Volumen v where v not in (select sv.volumen from SuscriptionVolumen sv where sv.customer.id=?1) ")
	Collection<Volumen> getVolumensNotSuscribedByCustomer(Integer customerId);

	@Query("select 1.0*avg(v.newspapers.size) from Volumen v")
	Double avgOfNewspaperPerVolumen();

	@Query("select v from Volumen v where v.user.id=?1 AND (select n from Newspaper n where n.id=?2) not member of v.newspapers")
	Collection<Volumen> getAvailaleVolumens(Integer userId, Integer newspaperId);
	
	//Paginated repository
	@Query("select v from Volumen v where v not in (select sv.volumen from SuscriptionVolumen sv where sv.customer.id=?1) ")
	Page<Volumen> getVolumensNotSuscribedByCustomerPaginate(Integer customerId, Pageable p);
	
	@Query("select v from Volumen v")
	Page<Volumen> findAllPaginate(Pageable p);
	
	@Query("select v from Volumen v where v.user.id=?1")
	Page<Volumen> getVolumensByUserPaginate(Integer userId, Pageable p);
	
	@Query("select sv.volumen from SuscriptionVolumen sv where sv.customer.id=?1")
	Page<Volumen> getVolumensByCustomerPaginate(Integer customerId, Pageable p);
	
	@Query("select n from Volumen v join v.newspapers n where v.id=?1")
	Page<Newspaper> getAllNewspaper(Integer id, Pageable p);
	
}
