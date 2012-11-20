package org.rich;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;
//import javax.faces.context.FacesContext;
//import javax.persistence.EntityManager;

import org.richfaces.component.SortOrder;

import com.google.common.collect.Maps;

/**
 * @author Nick Belaevski
 */
@ManagedBean
@SessionScoped
public class PersonBean implements Serializable {
    private static final long serialVersionUID = -5156711102367948040L;

    private static final class PersonDataModel extends ListDataModel<Person> {
        private PersonDataModel(List<Person> list) {
            super(list);
        }
    }

    private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);
    private Map<String, String> filterValues = Maps.newHashMap();
    private String sortProperty;

    public PersonBean() {
        sortOrders.put("name", SortOrder.unsorted);
        sortOrders.put("surname", SortOrder.unsorted);
        sortOrders.put("email", SortOrder.unsorted);
    }

//    private EntityManager lookupEntityManager() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        PersistenceService persistenceService = facesContext.getApplication().evaluateExpressionGet(facesContext,
//                "#{persistenceService}", PersistenceService.class);
//        return persistenceService.getEntityManager();
//    }

    public Map<String, SortOrder> getSortOrders() {
        return sortOrders;
    }

    public Map<String, String> getFilterValues() {
        return filterValues;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortPropety) {
        this.sortProperty = sortPropety;
    }

    public void toggleSort() {
        for (Entry<String, SortOrder> entry : sortOrders.entrySet()) {
            SortOrder newOrder;

            if (entry.getKey().equals(sortProperty)) {
                if (entry.getValue() == SortOrder.ascending) {
                    newOrder = SortOrder.descending;
                } else {
                    newOrder = SortOrder.ascending;
                }
            } else {
                newOrder = SortOrder.unsorted;
            }

            entry.setValue(newOrder);
        }
    }

    public Object getDataModel() {
        ArrayList<Person> list = new ArrayList<>();
        for(int i=0; i<100; i++) {
            Person p = new Person("name" + i);
            list.add(p);
        }
        return new PersonDataModel(lookupEntityManager());
    }
}