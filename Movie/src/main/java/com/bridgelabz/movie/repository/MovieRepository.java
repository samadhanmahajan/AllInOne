package com.bridgelabz.movie.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.movie.model.Movie;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{

}
