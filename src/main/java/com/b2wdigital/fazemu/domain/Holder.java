package com.b2wdigital.fazemu.domain;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author dailton.almeida
 */
public class Holder {

    private String v1;
    private String v2;

    public static Holder build(String v1, String v2) {
        Holder result = new Holder();
        result.setV1(v1);
        result.setV2(v2);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof Holder) {
            Holder other = (Holder) o;
            return new EqualsBuilder()
                    .append(this.v1, other.v1)
                    .append(this.v2, other.v2)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("v1", v1)
                .append("v2", v2)
                .toString();
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }
}
