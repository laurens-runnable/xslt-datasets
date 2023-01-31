<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
>

    <xsl:include href="../page.html.xslt"/>

    <xsl:template match="datasets">
        <div class="row">
            <div class="col">
                <table class="table">
                    <thead>
                        <th>Dataset</th>
                        <th colspan="4">Alternative formats</th>
                    </thead>
                    <tbody>
                        <xsl:for-each select="dataset">
                            <xsl:variable name="basePath" select="concat('/datasets/', @name)"/>
                            <tr>
                                <td>
                                    <a href="{$basePath}.html">
                                        <xsl:value-of select="@name"/>
                                    </a>
                                </td>
                                <td>
                                    <a href="{$basePath}.xml">
                                        XML
                                    </a>
                                </td>
                                <td>
                                    <a href="{$basePath}.pdf">PDF</a>
                                </td>
                                <td>
                                    <a href="{$basePath}.pdf?portrait=false">PDF landscape</a>
                                </td>
                                <td>
                                    <a href="{$basePath}.csv">
                                        CSV
                                    </a>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>


</xsl:stylesheet>
