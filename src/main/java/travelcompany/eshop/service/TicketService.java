package travelcompany.eshop.service;

import travelcompany.eshop.domain.Ticket;
import travelcompany.eshop.domain.enumer.PurchaseMethod;
import travelcompany.eshop.domain.exception.BusinessException;

public interface TicketService extends BaseService<Ticket> {
    void publishTicket(int customerCode, int itineraryCode, PurchaseMethod purchaseMethod) throws BusinessException;
}
