package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cpm.base.Activity;
import org.cpm.base.Event;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFlow {
    private Activity activity;
    private Event eventStart;
    private Event eventEnd;

    @Override
    public String toString() {
        return "ActivityFlow{" +
                "activity=" + activity +
                ", eventStart=" + eventStart +
                ", eventEnd=" + eventEnd +
                '}';
    }
}
