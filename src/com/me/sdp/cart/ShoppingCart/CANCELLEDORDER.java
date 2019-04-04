package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>CancelledOrder</code>.
 *  Column Name and Table Name of  database table  <code>CancelledOrder</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #CANCEL_ID}
  * </ul>
 */
 
public final class CANCELLEDORDER
{
    private CANCELLEDORDER()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "CancelledOrder" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String CANCEL_ID= "CANCEL_ID" ;

    /*
    * The index position of the column CANCEL_ID in the table.
    */
    public static final int CANCEL_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String ORDER_ID= "ORDER_ID" ;

    /*
    * The index position of the column ORDER_ID in the table.
    */
    public static final int ORDER_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String PRODUCT_ID= "PRODUCT_ID" ;

    /*
    * The index position of the column PRODUCT_ID in the table.
    */
    public static final int PRODUCT_ID_IDX = 3 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String PRODUCT_QUANTITY= "PRODUCT_QUANTITY" ;

    /*
    * The index position of the column PRODUCT_QUANTITY in the table.
    */
    public static final int PRODUCT_QUANTITY_IDX = 4 ;

    /**
                            * Data Type of this field is <code>DATETIME</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String TIMESTAMP= "TIMESTAMP" ;

    /*
    * The index position of the column TIMESTAMP in the table.
    */
    public static final int TIMESTAMP_IDX = 5 ;

}
