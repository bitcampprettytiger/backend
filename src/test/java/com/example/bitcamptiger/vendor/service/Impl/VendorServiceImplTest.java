package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.vendor.entity.BusinessDay;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorOpenStatus;
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
            vendor.setVendorOpenStatus(VendorOpenStatus.OPEN);
            vendor.setAddress("주소 테스트" + i);
            vendor.setLat(i);
            vendor.setLon(i);
            vendor.setTel("전화번호 테스트" + i);
            vendor.setBusinessDay(BusinessDay.FRI);
            vendor.setOpen(LocalTime.now());
            vendor.setClose(LocalTime.now());
            vendor.setMenu("메뉴 테스트" + i);

            Vendor savedVendor = vendorRepository.save(vendor);

            System.out.println(savedVendor.toString());

        }
    }

    @Test
    void insertVendor() {

        Vendor vendor = new Vendor();
        vendor.setVendorType("노점");
        vendor.setVendorName("붕어붕어");
        vendor.setVendorOpenStatus(VendorOpenStatus.OPEN);
        vendor.setAddress("서울특별시 강남구 강남대로 94길 20");
        vendor.setLat(192.06565);
        vendor.setLon(165.566555);
        vendor.setTel("01022226666");
        vendor.setBusinessDay(BusinessDay.FRI);
        vendor.setOpen(LocalTime.now());
        vendor.setClose(LocalTime.now());
        vendor.setMenu("팥붕, 슈붕");

        Vendor savedVendor = vendorRepository.save(vendor);

        System.out.println(savedVendor.toString());
    }

    @Test
    void updateVendor() {
        // Create a vendor
        Vendor vendor = new Vendor();
        vendor.setVendorType("노점");
        vendor.setVendorName("붕어붕어");
        vendor.setVendorOpenStatus(VendorOpenStatus.OPEN);
        vendor.setAddress("서울특별시 강남구 강남대로 94길 20");
        vendor.setLat(192.06565);
        vendor.setLon(165.566555);
        vendor.setTel("01022226666");
        vendor.setBusinessDay(BusinessDay.FRI);
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
        vendor.setVendorOpenStatus(VendorOpenStatus.OPEN);
        vendor.setAddress("서울특별시 강남구 강남대로 94길 20");
        vendor.setLat(192.06565);
        vendor.setLon(165.566555);
        vendor.setTel("01022226666");
        vendor.setBusinessDay(BusinessDay.FRI);
        vendor.setOpen(LocalTime.now());
        vendor.setClose(LocalTime.now());
        vendor.setMenu("팥붕, 슈붕");

        Vendor savedVendor = vendorRepository.save(vendor);
        System.out.println(savedVendor.toString());

        // Delete the saved vendor
        vendorRepository.deleteById(savedVendor.getId());
        Optional<Vendor> optionalDeletedVendor = vendorRepository.findById(savedVendor.getId());

        assertFalse(optionalDeletedVendor.isPresent(), "Vendor not deleted.");
        System.out.println(optionalDeletedVendor.toString());
    }

}