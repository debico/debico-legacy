package br.com.debico.ui.controllers;

import static com.google.common.base.Objects.equal;

import java.io.Serializable;

import com.google.common.base.Objects;

public class ViewOptions implements Serializable {

    private static final long serialVersionUID = 1648881202616650201L;

    private boolean enableGa;
	
	private boolean gaLocalhost;
	
	private String gaWebPropertyId;
	
	public ViewOptions() {

	}

	public boolean isEnableGa() {
		return enableGa;
	}

	public void setEnableGa(boolean enableGa) {
		this.enableGa = enableGa;
	}

	public String getGaWebPropertyId() {
		return gaWebPropertyId;
	}

	public void setGaWebPropertyId(String gaWebPropertyId) {
		this.gaWebPropertyId = gaWebPropertyId;
	}

    public final boolean isGaLocalhost() {
        return gaLocalhost;
    }

    public final void setGaLocalhost(boolean gaLocalhost) {
        this.gaLocalhost = gaLocalhost;
    }
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        ViewOptions that = (ViewOptions) obj;

        return equal(this.gaWebPropertyId, that.getGaWebPropertyId())
                && equal(this.gaLocalhost, that.isGaLocalhost())
                && equal(this.enableGa, that.isEnableGa());
	}
	
	@Override
	public int hashCode() {
	    return Objects.hashCode(this.enableGa, this.gaLocalhost, this.gaWebPropertyId);
	}
}
