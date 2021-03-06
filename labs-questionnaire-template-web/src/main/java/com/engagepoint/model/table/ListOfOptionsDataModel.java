package com.engagepoint.model.table;

import com.engagepoint.model.question.utils.VariantItem;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for enabling selection in the dataTable.
 */
public class ListOfOptionsDataModel extends ListDataModel<VariantItem> implements SelectableDataModel<VariantItem> {

    public ListOfOptionsDataModel(List<VariantItem> data) {
        super(data);
    }

    public ListOfOptionsDataModel() {
        super(new ArrayList<VariantItem>());
    }

    @Override
    public Object getRowKey(VariantItem object) {
        return object.getValue();
    }

    /**
     * Get row from table by variant value.
     *
     * @param rowKey variant value
     * @return row data (VariantItem object)
     */
    @Override
    public VariantItem getRowData(String rowKey) {
        List<VariantItem> itemList = (List<VariantItem>) getWrappedData();
        for (VariantItem variantItem : itemList) {
            if (variantItem.getValue().equals(rowKey)) {
                return variantItem;
            }
        }
        return null;
    }
}
