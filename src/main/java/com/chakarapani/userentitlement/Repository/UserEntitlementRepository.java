package com.chakarapani.userentitlement.Repository;

import com.chakarapani.base.Entity.Entitlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntitlementRepository extends JpaRepository<Entitlement, UUID> {
    Entitlement findByUsername(String username);
}
