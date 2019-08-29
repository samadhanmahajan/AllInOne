package com.bridgelabz.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.movie.model.Producer;
@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

}
