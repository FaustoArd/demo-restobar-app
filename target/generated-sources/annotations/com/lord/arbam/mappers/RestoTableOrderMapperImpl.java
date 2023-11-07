package com.lord.arbam.mappers;

import com.lord.arbam.dtos.RestoTableOrderDto;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.RestoTableOrder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T21:29:59-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class RestoTableOrderMapperImpl implements RestoTableOrderMapper {

    @Override
    public RestoTableOrder toOrder(RestoTableOrderDto restoTableOrderDto) {
        if ( restoTableOrderDto == null ) {
            return null;
        }

        RestoTableOrder.RestoTableOrderBuilder restoTableOrder = RestoTableOrder.builder();

        restoTableOrder.product( restoTableOrderDtoToProduct( restoTableOrderDto ) );
        restoTableOrder.id( restoTableOrderDto.getId() );
        restoTableOrder.productQuantity( restoTableOrderDto.getProductQuantity() );
        restoTableOrder.totalOrderPrice( restoTableOrderDto.getTotalOrderPrice() );

        return restoTableOrder.build();
    }

    @Override
    public RestoTableOrderDto toOrderDto(RestoTableOrder restoTableOrder) {
        if ( restoTableOrder == null ) {
            return null;
        }

        RestoTableOrderDto restoTableOrderDto = new RestoTableOrderDto();

        restoTableOrderDto.setProductId( restoTableOrderProductId( restoTableOrder ) );
        restoTableOrderDto.setProductName( restoTableOrderProductProductName( restoTableOrder ) );
        restoTableOrderDto.setId( restoTableOrder.getId() );
        restoTableOrderDto.setProductQuantity( restoTableOrder.getProductQuantity() );
        restoTableOrderDto.setTotalOrderPrice( restoTableOrder.getTotalOrderPrice() );

        return restoTableOrderDto;
    }

    @Override
    public List<RestoTableOrderDto> toOrdersDto(List<RestoTableOrder> orders) {
        if ( orders == null ) {
            return null;
        }

        List<RestoTableOrderDto> list = new ArrayList<RestoTableOrderDto>( orders.size() );
        for ( RestoTableOrder restoTableOrder : orders ) {
            list.add( toOrderDto( restoTableOrder ) );
        }

        return list;
    }

    protected Product restoTableOrderDtoToProduct(RestoTableOrderDto restoTableOrderDto) {
        if ( restoTableOrderDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( restoTableOrderDto.getProductId() );
        product.productName( restoTableOrderDto.getProductName() );

        return product.build();
    }

    private Long restoTableOrderProductId(RestoTableOrder restoTableOrder) {
        if ( restoTableOrder == null ) {
            return null;
        }
        Product product = restoTableOrder.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String restoTableOrderProductProductName(RestoTableOrder restoTableOrder) {
        if ( restoTableOrder == null ) {
            return null;
        }
        Product product = restoTableOrder.getProduct();
        if ( product == null ) {
            return null;
        }
        String productName = product.getProductName();
        if ( productName == null ) {
            return null;
        }
        return productName;
    }
}
