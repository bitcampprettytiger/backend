package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VendorServiceImplTest {

    @Autowired
    public VendorRepository vendorRepository;

    @Test
    void getVendorList() {
    }

    @Test
    void getOpenList() {

        for(int i = 1; i <= 10; i++) {
            Vendor vendor = new Vendor();
            vendor.setVendorType("타입 테스트" + i);
            vendor.setVendorName("이름 테스트" + i);
            vendor.setVendorOpenStatus("OPEN");
            vendor.setAddress("주소 테스트" + i);
            vendor.setX("127.11"+i);
            vendor.setY("98.333" + i);
            vendor.setTel("전화번호 테스트" + i);
            vendor.setBusinessDay("FRI");
            vendor.setOpen(LocalTime.now());
            vendor.setClose(LocalTime.now());
            vendor.setMenu("메뉴 테스트" + i);

            Vendor savedVendor = vendorRepository.save(vendor);

            assertEquals(vendorRepository.findByVendorName(savedVendor.getVendorName()).get(0), vendor);

        }
    }

    @Test
    void insertVendor() {

        Vendor vendor = new Vendor();
        vendor.setVendorType("노점");
        vendor.setVendorName("붕어붕어");
        vendor.setVendorOpenStatus("open");
        vendor.setAddress("서울특별시 강남구 강남대로 94길 20");
        vendor.setX("192.06565");
        vendor.setY("165.566555");
        vendor.setTel("01022226666");
        vendor.setBusinessDay("FRI");
        vendor.setOpen(LocalTime.now());
        vendor.setClose(LocalTime.now());
        vendor.setMenu("팥붕, 슈붕");

        Vendor savedVendor = vendorRepository.save(vendor);

        assertEquals(vendorRepository.findByVendorName(savedVendor.getVendorName()).get(0), vendor);
    }

    @Test
    void updateVendor() {
        // Create a vendor
        Vendor vendor = new Vendor();
        vendor.setVendorType("노점");
        vendor.setVendorName("붕어붕어");
        vendor.setVendorOpenStatus("OPEN");
        vendor.setAddress("서울특별시 강남구 강남대로 94길 20");
        vendor.setX("192.06565");
        vendor.setY("165.566555");
        vendor.setTel("01022226666");
        vendor.setBusinessDay("FRI");
        vendor.setOpen(LocalTime.now());
        vendor.setClose(LocalTime.now());
        vendor.setMenu("팥붕, 슈붕");

        Vendor savedVendor = vendorRepository.save(vendor);

        // Update the saved vendor
        savedVendor.setVendorName("붕어붕어 업데이트");
        savedVendor.setAddress("주소 업데이트");
        Vendor updatedVendor = vendorRepository.save(savedVendor);

        assertNotNull(updatedVendor, "Updated Vendor is null.");
        assertEquals(savedVendor.getId(), updatedVendor.getId(), "Updated Vendor has a different ID");
        assertEquals("붕어붕어 업데이트", updatedVendor.getVendorName(), "Vendor name not updated.");
        assertEquals("주소 업데이트", updatedVendor.getAddress(), "Vendor address not updated.");
        System.out.println(updatedVendor.toString());
    }

    @Test
    void deleteVendor() {
        // Create a vendor
        Vendor vendor = new Vendor();
        vendor.setVendorType("노점");
        vendor.setVendorName("붕어붕어");
        vendor.setVendorOpenStatus("OPEN");
        vendor.setAddress("서울특별시 강남구 강남대로 94길 20");
        vendor.setX("192.06565");
        vendor.setY("165.566555");
        vendor.setTel("01022226666");
        vendor.setBusinessDay("FRI");
        vendor.setOpen(LocalTime.now());
        vendor.setClose(LocalTime.now());
        vendor.setMenu("팥붕, 슈붕");

        Vendor savedVendor = vendorRepository.save(vendor);
        System.out.println(savedVendor.toString());

        // Delete the saved vendor
        vendorRepository.deleteById(savedVendor.getId());
        Optional<Vendor> optionalDeletedVendor = vendorRepository.findById(savedVendor.getId());

        assertFalse(optionalDeletedVendor.isPresent(), "Vendor not deleted.");
        assertTrue(optionalDeletedVendor.isEmpty(),"Vendor deleted");
        System.out.println(optionalDeletedVendor.toString());
    }

}