/*
 * This file is part of the Meeds project (https://meeds.io/).
 * Copyright (C) 2020 Meeds Association
 * contact@meeds.io
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.exoplatform.addons.gamification.service.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.exoplatform.addons.gamification.entities.domain.configuration.DomainEntity;
import org.exoplatform.addons.gamification.service.dto.configuration.DomainDTO;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;


public class DomainMapper {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final Log LOG = ExoLogger.getLogger(DomainMapper.class);

    public DomainMapper() {
    }

    public DomainDTO domainToDomainDTO(DomainEntity domain) {
        return new DomainDTO(domain);
    }

    public List<DomainDTO> domainssToDomainDTOs(List<DomainEntity> domains) {
        return domains.stream()
                .filter(Objects::nonNull)
                .map((DomainEntity domain) -> domainToDomainDTO(domain))
                .collect(Collectors.toList());
    }

    public DomainEntity domainDTOToDomain(DomainDTO domainDTO) {

        if (domainDTO == null) {
            return null;
        } else {
            DomainEntity domain = new DomainEntity();
            domain.setId(domainDTO.getId());
            domain.setTitle(domainDTO.getTitle());
            domain.setDescription(domainDTO.getDescription());
            domain.setCreatedBy(domainDTO.getCreatedBy());
            domain.setLastModifiedBy(domainDTO.getLastModifiedBy());
            domain.setDeleted(domainDTO.isDeleted());
            domain.setEnabled(domainDTO.isEnabled());
            domain.setLastModifiedDate(domainDTO.getLastModifiedDate());
            return domain;
        }
    }

    public List<DomainEntity> domainDTOsToDomains(List<DomainDTO> domainDTOs) {
        return domainDTOs.stream()
                .filter(Objects::nonNull)
                .map((DomainDTO domainDTO) -> domainDTOToDomain(domainDTO))
                .collect(Collectors.toList());
    }

    public DomainEntity domainFromId(Long id) {
        if (id == null) {
            return null;
        }
        DomainEntity domain = new DomainEntity();
        domain.setId(id);
        return domain;
    }

}
