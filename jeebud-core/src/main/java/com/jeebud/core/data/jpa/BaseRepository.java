package com.jeebud.core.data.jpa;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.core.data.jpa.domain.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@NoRepositoryBean
public interface BaseRepository<T, D> extends JpaRepository<T, D>, JpaSpecificationExecutor<T> {

    /**
     * 查询list
     *
     * @param query
     * @return
     */
    List<T> search(@Nullable Query query);

    /**
     * 查询page
     *
     * @param pageQuery
     * @return
     */
    Page<T> search(@Nullable PageQuery pageQuery);

    /**
     * 查询数量
     *
     * @param query
     * @return
     */
    long countSearch(@Nullable Query query);

}
