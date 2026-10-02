package net.tayebi.billingservice.repositories;

import net.tayebi.billingservice.entities.Bill;
import net.tayebi.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource

public interface BillRepository extends JpaRepository<Bill, Long> {
}
