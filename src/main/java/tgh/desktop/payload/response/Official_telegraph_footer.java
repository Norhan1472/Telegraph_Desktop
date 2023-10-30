package tgh.desktop.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Official_telegraph_footer {
    String delivery;
    String recName;
    String address;
    String officeName;
    String message;
    String senderName;
    String senderAddress;

}
