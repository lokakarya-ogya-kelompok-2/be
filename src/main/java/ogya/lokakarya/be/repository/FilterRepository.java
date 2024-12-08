package ogya.lokakarya.be.repository;

import java.util.List;

public interface FilterRepository<T1, T2> {
    List<T1> findAllByFilter(T2 filter);
}
