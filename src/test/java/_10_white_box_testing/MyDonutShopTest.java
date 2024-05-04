package _10_white_box_testing;

import _09_intro_to_white_box_testing.models.Order;
import _10_white_box_testing.models.BakeryService;
import _10_white_box_testing.models.PaymentService;
import _09_intro_to_white_box_testing.models.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MyDonutShopTest {

    @Mock
    PaymentService paymentServiceMock;

    @Mock
    DeliveryService deliveryServiceMock;

    @Mock
    BakeryService bakeryServiceMock;

    MyDonutShop myDonutShop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        myDonutShop = new MyDonutShop(paymentServiceMock, deliveryServiceMock, bakeryServiceMock);
    }

    @Test
    void itShouldTakeDeliveryOrder() throws Exception {
        //given
        Order order = new Order("John Doe", "1234567890", 6, 10.00, "1234-5678-9012-3456", true);

        //when
        myDonutShop.openForTheDay();

        
        Exception exception = assertThrows(Exception.class, () -> myDonutShop.takeOrder(order));

        //then
        verify(paymentServiceMock, times(1)).charge(order);
        verify(bakeryServiceMock, times(1)).removeDonuts(order.getNumberOfDonuts());
        verify(deliveryServiceMock, times(1)).scheduleDelivery(order);

        
        assertEquals("Insufficient donuts remaining", exception.getMessage());
    }


    @Test
    void givenInsufficientDonutsRemaining_whenTakeOrder_thenThrowIllegalArgumentException() {
        //given
        Order order = new Order("Jane Smith", "0987654321", 12, 20.00, "9876-5432-1098-7654", false);
        when(bakeryServiceMock.getDonutsRemaining()).thenReturn(10); 

        //when
        myDonutShop.openForTheDay();

        //then
        assertThrows(IllegalArgumentException.class, () -> myDonutShop.takeOrder(order));
    }

    @Test
    void givenNotOpenForBusiness_whenTakeOrder_thenThrowIllegalStateException(){
        //given
        Order order = new Order("Alice Johnson", "5556667777", 3, 5.00, "1111-2222-3333-4444", true);

        //when
        myDonutShop.closeForTheDay();

        //then
        assertThrows(IllegalStateException.class, () -> myDonutShop.takeOrder(order));
    }
}
