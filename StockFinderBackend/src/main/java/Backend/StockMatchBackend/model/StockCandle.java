package Backend.StockFinderBackend.model;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_candles")
public class StockCandle {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockTable stock;

    private String ticker;
    private Instant uniqueCandleTimestamp;

    // Using BigDecimal for financial data to avoid precision issues
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;

    // Additional field for volume, nullable
    private Long volume;

}
