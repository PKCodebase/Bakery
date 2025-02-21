package com.Online_Bakery.Repository;

import com.Online_Bakery.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
