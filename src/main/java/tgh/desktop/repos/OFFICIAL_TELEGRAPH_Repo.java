package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.OFFICIAL_TELEGRAPH;
import tgh.desktop.payload.response.OFFICIAL_TELEGRAPH_Response;

@Repository
public interface OFFICIAL_TELEGRAPH_Repo extends JpaRepository<OFFICIAL_TELEGRAPH,Long> {
    @Query(value = "select" +
            " Report_seq.nextval as \"Report_No\",\n" +
            "\"TGH_POST_OFFICE\".\"OFFICE_NAME\" OFFICE_NAME,\n" +
            "\"TGH_POST_OFFICE1\".\"OFFICE_NAME\" \n" +
            "OUTGOING_OFFICE,\n" +
            "'برقيه' as type,\n" +
            "\"OFFICIAL_TELEGRAPH\".\"ORIGIN_OFFICE\"\n" +
            ",to_char(\"OFFICIAL_TELEGRAPH\".\"TGH_DATE\",'DD-MM-RRRR hh24:mi') as \"TGH_Date\",\n" +
            "NVL(to_char(action_date,'DD-MM-RRRR hh24:mi') ,to_char(\"OFFICIAL_TELEGRAPH\".\"TGH_DATE\",'DD-MM-RRRR hh24:mi')) as \"action_Date\",\n" +
            "\"OFFICIAL_TELEGRAPH\".\"TGH_CODE\",\n" +
            "--seq_no\n" +
            "\"OFFICIAL_TELEGRAPH\".\"SEQ_NO\" \"SEQ_NO\",\n" +
            "--رقم المستند\n" +
            "\"OFFICIAL_TELEGRAPH\".\"TGH_ID\" as \"TGH_ID\",\n" +
            "-- رقم المحاسبه\n" +
            "0||\"OFFICIAL_TELEGRAPH\".\"BILL_TEL_NO\" BILL_TEL_NO,\n" +
            "--اسم المبلغ\n" +
            "\"OFFICIAL_TELEGRAPH\".\"CALLER_NAME\" as \"CALLER_NAME\",\n" +
            "--كود الدوله\n" +
            "\"TGH_POST_OFFICE\".\"COUNTRY_CODE\",\n" +
            "--عدد الكلمات\n" +
            "\"OFFICIAL_TELEGRAPH\".\"NO_OF_WORDS\",\n" +
            "--بعلم الوصول\n" +
            "delivery,\n" +
            "--rec_name\n" +
            "\"OFFICIAL_TELEGRAPH\".\"REC_NAME\",\n" +
            "--address\n" +
            "\"OFFICIAL_TELEGRAPH\".\"ADDRESS\",\n" +
            "--message\n" +
            "to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE\") Message,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE2\") Message2,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE3\") Message3,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE4\") Message4,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE5\") Message5,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE6\") Message6,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE7\") Message7,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE8\") Message8,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE9\") Message9,to_char(\"OFFICIAL_TELEGRAPH\".\"MESSAGE10\") Message10,\n" +
            "--sender_name\n" +
            "to_char(\"OFFICIAL_TELEGRAPH\".\"SENDER_NAME\") \"SENDER_NAME\",\n" +
            "--sender_address\n" +
            "'عنوان الراسل:   '||\"OFFICIAL_TELEGRAPH\".\"SENDER_ADDRESS\" as SENDER_ADDRESS\n" +
            "from\t \"OFFICIAL_TELEGRAPH\",TGH_POST_OFFICE TGH_POST_OFFICE,TGH_POST_OFFICE TGH_POST_OFFICE1\n" +
            "where   \"OFFICIAL_TELEGRAPH\".\"OFFICE_CODE\"=\"TGH_POST_OFFICE\".\"OFFICE_CODE\"\n" +
            "and \"OFFICIAL_TELEGRAPH\".\"ORIGIN_OFFICE\"=\"TGH_POST_OFFICE1\".\"OFFICE_CODE\"\n" +
            "and ((trim(OFFICIAL_TELEGRAPH.TGH_CODE) =:TghCode and :SEQ is null) \n" +
            "   or\t (:TghCode is null and OFFICIAL_TELEGRAPH.tgh_id=:SEQ) or\t (trim(OFFICIAL_TELEGRAPH.TGH_CODE) =:TghCode and OFFICIAL_TELEGRAPH.tgh_id=:SEQ))",
            nativeQuery = true
    )
    OFFICIAL_TELEGRAPH_Response getOfficialTelegraphPage(long SEQ,String TghCode);

   // void searchOfficialTelegraph();

}
