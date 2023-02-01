<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:include href="../page.html.xslt"/>

    <xsl:template match="datasets">
        <div class="row">
            <div class="col">
                <table class="table">
                    <thead>
                        <th>Dataset</th>
                        <th>Type</th>
                        <th colspan="3">Formats</th>
                    </thead>
                    <tbody>
                        <xsl:for-each select="dataset">
                            <xsl:variable name="basePath" select="concat('/datasets/', @name)"/>
                            <tr>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test="@hasHtmlFormat = 'true'">
                                            <a href="{$basePath}.html">
                                                <xsl:value-of select="@name"/>
                                            </a>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="@name"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td>
                                    <xsl:value-of select="lower-case(@type)"/>
                                </td>
                                <td>
                                    <a href="{$basePath}.xml">
                                        XML
                                    </a>
                                </td>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test="@hasCsvFormat = 'true'">
                                            <a href="{$basePath}.csv">
                                                CSV
                                            </a>
                                        </xsl:when>
                                        <xsl:otherwise>n/a</xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test="@hasPdfFormat = 'true'">
                                            <a href="{$basePath}.pdf">PDF</a>
                                            <small>
                                                (<a href="{$basePath}.pdf?portrait=false">landscape</a>)
                                            </small>
                                        </xsl:when>
                                        <xsl:otherwise>n/a</xsl:otherwise>
                                    </xsl:choose>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
