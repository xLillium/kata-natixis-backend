package com.xlillium.kata_natixis_backend.mappers;

import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import com.xlillium.kata_natixis_backend.models.Book;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    BookDTO toDto(Book book);

    Book toEntity(BookDTO bookDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntityWithDto(BookDTO bookDTO, @MappingTarget Book existingBook);
}
