package com.awsbedrock.api.repository;

import com.awsbedrock.api.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ============================================================
 * JobRepository — Spring Data JPA Repository
 * ============================================================
 *
 * Spring Data JPA auto-generates the implementation for this interface.
 * You only declare the method signatures — Spring writes the SQL!
 *
 * BUILT-IN METHODS (from JpaRepository):
 *   findAll()             → SELECT * FROM jobs
 *   findById(id)          → SELECT * FROM jobs WHERE id = ?
 *   save(job)             → INSERT or UPDATE
 *   deleteById(id)        → DELETE FROM jobs WHERE id = ?
 *   count()               → SELECT COUNT(*) FROM jobs
 *
 * CUSTOM QUERY METHODS (Spring generates SQL from method names):
 *   findByCompany("TCS")  → SELECT * FROM jobs WHERE company = 'TCS'
 *   findByPlatform("Naukri") → SELECT * FROM jobs WHERE platform = 'Naukri'
 *
 * HOW SPRING DATA JPA NAMING CONVENTION WORKS:
 *   findBy + FieldName + Condition
 *   Examples:
 *     findByTitle(String title)
 *       → WHERE title = :title
 *     findByLocationContainingIgnoreCase(String keyword)
 *       → WHERE LOWER(location) LIKE LOWER('%keyword%')
 *     findByApplied(Integer applied)
 *       → WHERE applied = :applied
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Find all jobs by company name (case-insensitive partial match).
     *
     * Example: findByCompanyContainingIgnoreCase("TCS")
     *   → matches "Tata Consultancy Services", "tcs", "TCS Ltd"
     */
    List<Job> findByCompanyContainingIgnoreCase(String company);

    /**
     * Find all jobs by platform.
     *
     * Example: findByPlatform("Naukri")
     *   → All jobs scraped from Naukri
     */
    List<Job> findByPlatform(String platform);

    /**
     * Find all jobs by location (case-insensitive partial match).
     *
     * Example: findByLocationContainingIgnoreCase("Bengaluru")
     *   → All jobs in Bengaluru
     */
    List<Job> findByLocationContainingIgnoreCase(String location);

    /**
     * Find all jobs the user has NOT applied to.
     *
     * Example: findByApplied(0) → All unapplied jobs
     */
    List<Job> findByApplied(Integer applied);

    /**
     * Find jobs matching a keyword in title OR description.
     * Uses custom JPQL (Java Persistence Query Language).
     *
     * Example: searchByKeyword("Java")
     *   → All jobs with "Java" in title or description
     *
     * JPQL NOTE:
     *   - Uses entity field names (title, description), NOT column names
     *   - LIKE with LOWER() for case-insensitive search
     *   - :keyword is a named parameter bound via @Param
     */
    @Query("SELECT j FROM Job j WHERE " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Job> searchByKeyword(@Param("keyword") String keyword);

    /**
     * Find jobs by title containing a keyword (case-insensitive).
     *
     * Example: findByTitleContainingIgnoreCase("Developer")
     *   → "Java Developer", "React JS Developer", "Scala Developer"
     */
    List<Job> findByTitleContainingIgnoreCase(String title);

    /**
     * Count jobs grouped by company — useful for analytics.
     * Returns a list of [company, count] arrays.
     *
     * Native SQL query (not JPQL) because of GROUP BY aggregation.
     */
    @Query(value = "SELECT company, COUNT(*) as job_count FROM linkedin_naukr_jobs.jobs " +
            "GROUP BY company ORDER BY job_count DESC", nativeQuery = true)
    List<Object[]> countJobsByCompany();

    /**
     * Count jobs grouped by location.
     */
    @Query(value = "SELECT location, COUNT(*) as job_count FROM linkedin_naukr_jobs.jobs " +
            "GROUP BY location ORDER BY job_count DESC", nativeQuery = true)
    List<Object[]> countJobsByLocation();
}
