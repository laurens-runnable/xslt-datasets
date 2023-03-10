<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:content="http://runnable.nl/dataset/content"
                xmlns:csv="http://runnable.nl/dataset/csv">

    <xsl:include href="../page.html.xslt"/>

    <xsl:param name="sortColumn" select="1"/>
    <xsl:param name="sortOrder" select="'ascending'"/>

    <xsl:template match="content">
        <table class="table">
            <xsl:apply-templates mode="thead"/>
            <xsl:apply-templates mode="tbody"/>
        </table>
    </xsl:template>

    <xsl:template match="csv:data" mode="thead">
        <thead>
            <xsl:for-each select="csv:row[1]/content:*">
                <th>
                    <xsl:apply-templates select="." mode="th"/>
                </th>
            </xsl:for-each>
        </thead>
    </xsl:template>

    <xsl:template match="csv:data" mode="tbody">
        <tbody>
            <xsl:for-each select="csv:row">
                <xsl:sort select="content:*[$sortColumn]" order="{$sortOrder}"/>
                <tr>
                    <xsl:for-each select="content:*">
                        <td>
                            <xsl:apply-templates select="."/>
                        </td>
                    </xsl:for-each>
                </tr>
            </xsl:for-each>
        </tbody>
    </xsl:template>

    <xsl:template match="content:*" mode="th">
        <xsl:value-of select="local-name(.)"/>
    </xsl:template>

</xsl:stylesheet>
