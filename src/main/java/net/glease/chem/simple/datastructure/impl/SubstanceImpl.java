
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.glease.chem.simple.datastructure.CrystalType;
import net.glease.chem.simple.datastructure.DoubleAdapter;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Substance", propOrder = { "atom", "dissovle" })
public class SubstanceImpl implements Serializable, Substance {

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class DissovleImpl implements Serializable, Substance.Dissovle {

		private final static long serialVersionUID = 1L;
		@XmlAttribute(name = "solvent", namespace = "http://glease.net/chem/simple/DataStructure", required = true)
		@XmlIDREF
		@XmlSchemaType(name = "IDREF")
		protected Reagent solvent;
		@XmlAttribute(name = "s2TFunction", required = true)
		protected String s2TFunction;

		@Override
		public String getS2TFunction() {
			return s2TFunction;
		}

		@Override
		public Reagent getSolvent() {
			return solvent;
		}

		@Override
		public void setS2TFunction(String value) {
			this.s2TFunction = value;
		}

		@Override
		public void setSolvent(Reagent value) {
			this.solvent = value;
		}

	}

	private final static long serialVersionUID = 1L;
	@XmlElement(required = true, type = SubstanceContentImpl.class)
	protected List<SubstanceContent> atom;
	@XmlElement(type = SubstanceImpl.DissovleImpl.class)
	protected List<Substance.Dissovle> dissovle;
	@XmlAttribute(name = "name", required = true)
	protected String name;
	@XmlAttribute(name = "meltPoint")
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	protected double meltPoint;
	@XmlAttribute(name = "boilPoint")
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	protected double boilPoint;
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	@XmlAttribute(name = "crystal")
	protected CrystalType crystal;

	@Override
	public List<SubstanceContent> getAtom() {
		if (atom == null) {
			atom = new ArrayList<SubstanceContent>();
		}
		return this.atom;
	}

	@Override
	public double getBoilPoint() {
		return boilPoint;
	}

	@Override
	public CrystalType getCrystal() {
		return crystal;
	}

	@Override
	public List<Substance.Dissovle> getDissovle() {
		if (dissovle == null) {
			dissovle = new ArrayList<Substance.Dissovle>();
		}
		return this.dissovle;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public double getMeltPoint() {
		return meltPoint;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setBoilPoint(double value) {
		this.boilPoint = value;
	}

	@Override
	public void setCrystal(CrystalType value) {
		this.crystal = value;
	}

	@Override
	public void setId(String value) {
		this.id = value;
	}

	@Override
	public void setMeltPoint(double value) {
		this.meltPoint = value;
	}

	@Override
	public void setName(String value) {
		this.name = value;
	}

}
