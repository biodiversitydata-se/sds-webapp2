package au.org.ala.biocache

import au.org.ala.checklist.lucene.model.NameSearchResult

object BiocacheConversions {

  implicit def asClassification(nsr: NameSearchResult): au.org.ala.biocache.Classification = {
    val cl = new au.org.ala.biocache.Classification
    val rankClassification = nsr.getRankClassification
    cl.kingdom = rankClassification.getKingdom
    cl.kingdomID = rankClassification.getKid
    cl.phylum = rankClassification.getPhylum
    cl.phylumID = rankClassification.getPid
    cl.classs = rankClassification.getKlass
    cl.classID = rankClassification.getCid
    cl.order = rankClassification.getOrder
    cl.orderID = rankClassification.getOid
    cl.family = rankClassification.getFamily
    cl.familyID = rankClassification.getFid
    cl.genus = rankClassification.getGenus
    cl.genusID = rankClassification.getGid
    cl.species = rankClassification.getSpecies
    cl.speciesID = rankClassification.getSid
    cl.specificEpithet = rankClassification.getSpecificEpithet
    cl.scientificName = rankClassification.getScientificName
    cl.taxonConceptID = nsr.getLsid
    cl.left = nsr.getLeft
    cl.right = nsr.getRight
    cl.taxonRank = nsr.getRank.getRank
    cl.taxonRankID = nsr.getRank.getId.toString
    cl
  }

}